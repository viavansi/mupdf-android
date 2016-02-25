package com.artifex.utils;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

public class PdfBitmap implements Parcelable {

	public enum Type {
		SIGNATURE,				// Signature used to sign the document
		SIGNATURE_USER_IMAGE, 	// User image of some older versions on Viafirma, where we sent the image to the server for it to process that along with the signature.
		IMAGE					// All generic images shown
	};

	private Bitmap image;
	private int height;
	private int width;
	private int pageNumber;
    private int pdfX;
    private int pdfY;
	private Type type;
	private boolean isRemovable;
	private HashMap<String, String> metadata;

    /**
     * This class is used to store the information of each stamp and annotation on the PDF.
     * @param image The bitmap in charge of storing the stamp or annotation
     * @param height The height defined for the drawing
     * @param width The width defined for the drawing
     * @param pdfX The X coordinate position defined for the drawing
     * @param pdfY The Y coordinate position defined for the drawing
     * @param page The page of the PDF where the bitmap is added
     */
	public PdfBitmap(Bitmap image, int width, int height, int pdfX, int pdfY, int page, Type type) {
		this.image = image;
		this.height = height;
		this.width = width;
        this.pdfX = pdfX;
        this.pdfY = pdfY;
		this.pageNumber = page;// first page is 0
		this.type = type;
		this.isRemovable = true;
		this.metadata = new HashMap<>();
	}
	
	public PdfBitmap(Parcel in) {
		// We just need to read back each
		// field in the order that it was
        image = in.readParcelable(Bitmap.class.getClassLoader());
        height = in.readInt();
        width = in.readInt();
        pdfX = in.readInt();
        pdfY = in.readInt();
        pageNumber = in.readInt();
		String typeString = in.readString();
		if (typeString != null) {
			type = Type.valueOf(typeString);
		}
		isRemovable = in.readByte() != 0;
		in.readMap(metadata, HashMap.class.getClassLoader());
	}
	
	public Bitmap getBitmapImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

    @Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image, flags);
		dest.writeInt(height);
		dest.writeInt(width);
        dest.writeInt(pdfX);
        dest.writeInt(pdfY);
		dest.writeInt(pageNumber);
		dest.writeString(type.name());
		dest.writeByte((byte)(isRemovable ? 1 : 0));
		dest.writeMap(metadata);
	}

	public static final Creator CREATOR = new Creator() {
		public PdfBitmap createFromParcel(Parcel in) {
			return new PdfBitmap(in);
		}
		public PdfBitmap[] newArray(int size) {
			return new PdfBitmap[size];
		}
	};

    public int getPdfX() {
        return pdfX;
    }

    public int getPdfY() {
        return pdfY;
    }

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isRemovable() {
		return isRemovable;
	}

	public void setIsRemovable(boolean isRemovable) {
		this.isRemovable = isRemovable;
	}

	public HashMap<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		this.metadata = metadata;
	}

	@Override
	public String toString() {
		String result = "page:"+pageNumber+", x:"+pdfX+", y:"+pdfY+", width:"+width+", height:"+height+", type:"+type.name();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		try {
			if (o == this) {
				result = true;
			} else if (o instanceof PdfBitmap) {
				PdfBitmap that = (PdfBitmap) o;
				boolean sameBitmaps = that.getBitmapImage().sameAs(image);
				result = (that.getPdfX() == pdfX) && (that.getPdfY() == pdfY) && (that.getHeight() == height) && (that.getWidth() == width) && (that.getPageNumber() == pageNumber) && sameBitmaps;
			}
		} catch (Exception e) {
			Log.e("PdfBitmap", e.getLocalizedMessage(), e);
		}
		return result;
	}
}
