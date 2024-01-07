package com.ryu.document_reader.widget.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.DisplayMetrics;

import java.lang.ref.WeakReference;

final class BitmapLoadingWorkerTask extends AsyncTask<Void, Void, BitmapLoadingWorkerTask.Result> {
  private final Context mContext;
  
  private final WeakReference<CropImageView> mCropImageViewReference;
  
  private final int mHeight;
  
  private final Uri mUri;
  
  private final int mWidth;
  
  public BitmapLoadingWorkerTask(CropImageView paramCropImageView, Uri paramUri) {
    double d;
    this.mUri = paramUri;
    this.mCropImageViewReference = new WeakReference<CropImageView>(paramCropImageView);
    this.mContext = paramCropImageView.getContext();
    DisplayMetrics displayMetrics = paramCropImageView.getResources().getDisplayMetrics();
    if (displayMetrics.density > 1.0F) {
      d = (1.0F / displayMetrics.density);
    } else {
      d = 1.0D;
    } 
    this.mWidth = (int)(displayMetrics.widthPixels * d);
    this.mHeight = (int)(displayMetrics.heightPixels * d);
  }
  
  protected Result doInBackground(Void... paramVarArgs) {
    try {
      if (!isCancelled()) {
        BitmapUtils.BitmapSampled bitmapSampled = BitmapUtils.decodeSampledBitmap(this.mContext, this.mUri, this.mWidth, this.mHeight);
        if (!isCancelled()) {
          BitmapUtils.RotateBitmapResult rotateBitmapResult = BitmapUtils.rotateBitmapByExif(bitmapSampled.bitmap, this.mContext, this.mUri);
          return new Result(this.mUri, rotateBitmapResult.bitmap, bitmapSampled.sampleSize, rotateBitmapResult.degrees);
        } 
      } 
      return null;
    } catch (Exception exception) {
      return new Result(this.mUri, exception);
    } 
  }
  
  public Uri getUri() {
    return this.mUri;
  }
  
  protected void onPostExecute(Result paramResult) {
    if (paramResult != null) {
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (!isCancelled()) {
        CropImageView cropImageView = this.mCropImageViewReference.get();
        bool1 = bool2;
        if (cropImageView != null) {
          bool1 = true;
          cropImageView.onSetImageUriAsyncComplete(paramResult);
        } 
      } 
      if (!bool1 && paramResult.bitmap != null)
        paramResult.bitmap.recycle(); 
    } 
  }
  
  public static final class Result {
    public final Bitmap bitmap;
    
    public final int degreesRotated;
    
    public final Exception error;
    
    public final int loadSampleSize;
    
    public final Uri uri;
    
    Result(Uri param1Uri, Bitmap param1Bitmap, int param1Int1, int param1Int2) {
      this.uri = param1Uri;
      this.bitmap = param1Bitmap;
      this.loadSampleSize = param1Int1;
      this.degreesRotated = param1Int2;
      this.error = null;
    }
    
    Result(Uri param1Uri, Exception param1Exception) {
      this.uri = param1Uri;
      this.bitmap = null;
      this.loadSampleSize = 0;
      this.degreesRotated = 0;
      this.error = param1Exception;
    }
  }
}


/* Location:              C:\Users\DucMH\Desktop\dex-tools-v2.4\classes-dex2jar.jar!\com\alldoucment\reader\viewer\widget\cropper\BitmapLoadingWorkerTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */