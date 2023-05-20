package com.kongpf8848.dmail.util

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.*

object Utils {
    fun extractAsset(
        context: Context,
        assetResId: Int,
        folderName: String?,
        fileName: String?
    ): File {
        val cacheFolder = File(context.cacheDir, folderName)
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs()
        }
        val outputFile = File(cacheFolder, fileName)
        if (!outputFile.exists() || outputFile.length() <= 0) {
            val buffer = ByteArray(4096)
            var byteCount = 0
            try {
                val `is` = context.resources.openRawResource(assetResId)
                val fos = FileOutputStream(outputFile)
                while (`is`.read(buffer).also { byteCount = it } != -1) {
                    fos.write(buffer, 0, byteCount)
                }
                fos.flush()
                `is`.close()
                fos.close()
            } catch (e: IOException) {
                outputFile.delete()
                e.printStackTrace()
            }
        }
        return outputFile
    }

    val defaultLocale: String
        get() {
            val locale = locale
            return locale.language + "_" + locale.country
        }
    val locale: Locale
        get() {
            val locale: Locale
            locale =
                LocaleList.getDefault()[0]
            return locale
        }

    @JvmStatic
    fun writeFile(path: String?, bytes: ByteArray?) {
        val file = File(path)
        var outputStream: FileOutputStream? = null
        var bufferedOutputStream: BufferedOutputStream? = null
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            outputStream = FileOutputStream(file)
            bufferedOutputStream = BufferedOutputStream(outputStream)
            bufferedOutputStream.write(bytes)
            bufferedOutputStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close()
                } catch (e2: Exception) {
                    e2.printStackTrace()
                }
            }
        }
    }
}