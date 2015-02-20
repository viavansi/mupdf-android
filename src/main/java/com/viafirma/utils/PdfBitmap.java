package com.viafirma.utils;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class PdfBitmap implements Parcelable {

	private static final long serialVersionUID = 1L;

	private static final int NO_IMAGE = -1;

	private Bitmap image;
	private int height;
	private int width;
	private int x;
	private int y;
	private int pageNumber;
    private int pdfX;
    private int pdfY;
    private boolean isSignature;

    /**
     * This class is used to store the information of each stamp and annotation on the PDF.
     * @param image The bitmap in charge of storing the stamp or annotation
     * @param height The height defined for the drawing
     * @param width The width defined for the drawing
     * @param x The X coordinate position defined for the drawing
     * @param y The Y coordinate position defined for the drawing
     * @param page The page of the PDF where the bitmap is added
     */
	public PdfBitmap(Bitmap image, int width, int height, int x, int y, int page, boolean isSignature) {
		this.image = image;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.pageNumber = page;// first page is 0
        this.isSignature = isSignature;
	}
	
	public PdfBitmap(Parcel in) {
		// We just need to read back each
		// field in the order that it was
        image = in.readParcelable(Bitmap.class.getClassLoader());
        height = in.readInt();
        width = in.readInt();
        x = in.readInt();
        y = in.readInt();
        pageNumber = in.readInt();
        pdfX = in.readInt();
        pdfY = in.readInt();
        isSignature = in.readByte() != 0;
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
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
		dest.writeInt(x);
		dest.writeInt(y);
		dest.writeInt(pageNumber);
        dest.writeInt(pdfX);
        dest.writeInt(pdfY);
        dest.writeByte((byte)(isSignature ? 1 : 0));
	}

	public static final Creator CREATOR = new Creator() {
		public PdfBitmap createFromParcel(Parcel in) {
			return new PdfBitmap(in);
		}
		public PdfBitmap[] newArray(int size) {
			return new PdfBitmap[size];
		}
	};

    public void savePdfXY(float pdfX, float pdfY) {
        //Se corrige la posicion de la firma para el PDF
        this.pdfX = (int)(pdfX - (width / 2.0f));
        this.pdfY = (int)(pdfY - (height / 2.0f));
    }

    public int getPdfX() {
        return pdfX;
    }

    public int getPdfY() {
        return pdfY;
    }

    public boolean isSignature() {
        return isSignature;
    }

    public void setSignature(boolean isSignature) {
        this.isSignature = isSignature;
    }
}
