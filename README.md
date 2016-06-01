# MuPDF for Android 

## Introduction
This project is intended to offer an easy integration of MuPDF library (http://www.mupdf.com) on Android, avoiding the building process and adapted to the last version of Android Studio and Gradle as of February 2015 (commit 262a4717a9997c89cac275d24ce6d605ca06284f from http://git.ghostscript.com/mupdf.git)

We also added some features:

* You can add custom Bitmaps to each page.
* You can use the MuPDFActivity as a Fragment (MuPDFFragment), that allows you to include it in your own activity as any other layout.
* You can add an interface listener to the page of the pdf, so you can listen when the user taps, double taps or long press any coordinate of the pdf.

This version is still on development.

## Installation guide

1. Make sure you have installed the newest NDK from https://developer.android.com/tools/sdk/ndk/index.html#Installing (version 9+ required)
