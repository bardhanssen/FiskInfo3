package com.ajhuntsman.ksftp.task

import com.ajhuntsman.ksftp.FilePair
import com.ajhuntsman.ksftp.SftpConnectionParameters
import com.jcraft.jsch.SftpException
import java.io.File

/**
 * Deletes one more remote files.
 */
internal class DeleteTask(sftpConnectionParameters: SftpConnectionParameters, filePairs: List<FilePair>) : BaseTask(sftpConnectionParameters, filePairs) {

    override fun doWork(): Boolean {
        return deleteFiles()
    }

    @Throws(Exception::class)
    private fun deleteFiles(): Boolean {
        if (filePairs.isEmpty()) {
            return true
        }

        try {
            val startTime = System.currentTimeMillis()

            var remotePath: String
            for (filePair in filePairs) {
                remotePath = filePair.sourceFilePath

                try {
                    deleteFile(remotePath)
                } catch (e: SftpException) {
                    throw e
                }
            }

            return true
        } catch (e: Exception) {
            throw e
        }
    }

    @Throws(SftpException::class)
    private fun deleteFile(remotePath: String) {
        // If the file doesn't exist, move on
        if (remotePath.isEmpty() || "." == remotePath || ".." == remotePath) {
            return
        }

        try {
            sftpChannel?.ls(remotePath)
        } catch (e: SftpException) {
            return
        }

        if (isDirectory(remotePath)) {
            // Change to the directory
            sftpChannel?.cd(remotePath)

            // Recursively delete all files in this directory
            val lsEntries = getLsEntriesForCurrentDirectory(sftpChannel)
            //KsftpLog.logDebug("Found '${lsEntries.size}' files in: '$remotePath'")
            for (entry in lsEntries) {
                deleteFile(entry.filename)
            }

            // NOTE: We can't delete a directory we are in, so we must change to the parent directory
            val fullDirectoryPath = sftpChannel?.pwd()
            if (sftpChannel?.pwd()?.endsWith(remotePath) ?: false) {
                val parentDirectory = fullDirectoryPath?.substring(0, fullDirectoryPath.lastIndexOf(File.separatorChar))
                //KsftpLog.logDebug("Changing to directory: '$parentDirectory'...")
                sftpChannel?.cd(parentDirectory);
            }

            //KsftpLog.logDebug("Attempting to delete directory: '$fullDirectoryPath'...")
            sftpChannel?.rmdir(fullDirectoryPath);
            //runExeCommand("rm -rf " + fullDirectoryPath)
        } else {
            //KsftpLog.logDebug("Attempting to delete file: '$remotePath'...")
            sftpChannel?.rm(remotePath);
        }
    }

    @Throws(SftpException::class)
    private fun isDirectory(remotePath: String): Boolean {
        return sftpChannel?.stat(remotePath)?.isDir ?: false
    }
}