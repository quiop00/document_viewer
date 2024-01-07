package com.ryu.document_reader.widget.cropper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

final class BitmapCroppingWorkerTask extends AsyncTask<Void, Void, BitmapCroppingWorkerTask.Result> {
  private final int mAspectRatioX;
  
  private final int mAspectRatioY;
  
  private final Bitmap mBitmap;
  
  private final Context mContext;
  
  private final WeakReference<CropImageView> mCropImageViewReference;
  
  private final float[] mCropPoints;
  
  private final int mDegreesRotated;
  
  private final boolean mFixAspectRatio;
  
  private final boolean mFlipHorizontally;
  
  private final boolean mFlipVertically;
  
  private final int mOrgHeight;
  
  private final int mOrgWidth;
  
  private final int mReqHeight;
  
  private final CropImageView.RequestSizeOptions mReqSizeOptions;
  
  private final int mReqWidth;
  
  private final Bitmap.CompressFormat mSaveCompressFormat;
  
  private final int mSaveCompressQuality;
  
  private final Uri mSaveUri;
  
  private final Uri mUri;
  
  BitmapCroppingWorkerTask(CropImageView paramCropImageView, Bitmap paramBitmap, float[] paramArrayOffloat, int paramInt1, boolean paramBoolean1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean2, boolean paramBoolean3, CropImageView.RequestSizeOptions paramRequestSizeOptions, Uri paramUri, Bitmap.CompressFormat paramCompressFormat, int paramInt6) {
    this.mCropImageViewReference = new WeakReference<CropImageView>(paramCropImageView);
    this.mContext = paramCropImageView.getContext();
    this.mBitmap = paramBitmap;
    this.mCropPoints = paramArrayOffloat;
    this.mUri = null;
    this.mDegreesRotated = paramInt1;
    this.mFixAspectRatio = paramBoolean1;
    this.mAspectRatioX = paramInt2;
    this.mAspectRatioY = paramInt3;
    this.mReqWidth = paramInt4;
    this.mReqHeight = paramInt5;
    this.mFlipHorizontally = paramBoolean2;
    this.mFlipVertically = paramBoolean3;
    this.mReqSizeOptions = paramRequestSizeOptions;
    this.mSaveUri = paramUri;
    this.mSaveCompressFormat = paramCompressFormat;
    this.mSaveCompressQuality = paramInt6;
    this.mOrgWidth = 0;
    this.mOrgHeight = 0;
  }
  
  BitmapCroppingWorkerTask(CropImageView paramCropImageView, Uri paramUri1, float[] paramArrayOffloat, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean2, boolean paramBoolean3, CropImageView.RequestSizeOptions paramRequestSizeOptions, Uri paramUri2, Bitmap.CompressFormat paramCompressFormat, int paramInt8) {
    this.mCropImageViewReference = new WeakReference<CropImageView>(paramCropImageView);
    this.mContext = paramCropImageView.getContext();
    this.mUri = paramUri1;
    this.mCropPoints = paramArrayOffloat;
    this.mDegreesRotated = paramInt1;
    this.mFixAspectRatio = paramBoolean1;
    this.mAspectRatioX = paramInt4;
    this.mAspectRatioY = paramInt5;
    this.mOrgWidth = paramInt2;
    this.mOrgHeight = paramInt3;
    this.mReqWidth = paramInt6;
    this.mReqHeight = paramInt7;
    this.mFlipHorizontally = paramBoolean2;
    this.mFlipVertically = paramBoolean3;
    this.mReqSizeOptions = paramRequestSizeOptions;
    this.mSaveUri = paramUri2;
    this.mSaveCompressFormat = paramCompressFormat;
    this.mSaveCompressQuality = paramInt8;
    this.mBitmap = null;
  }

  // TODO: check
  protected Result doInBackground(Void... paramVarArgs) {
    try {
      if (!isCancelled()) {
        BitmapUtils.BitmapSampled bitmapSampled;
        if (mUri != null) {
          bitmapSampled =
                  BitmapUtils.cropBitmap(
                          mContext,
                          mUri,
                          mCropPoints,
                          mDegreesRotated,
                          mOrgWidth,
                          mOrgHeight,
                          mFixAspectRatio,
                          mAspectRatioX,
                          mAspectRatioY,
                          mReqWidth,
                          mReqHeight,
                          mFlipHorizontally,
                          mFlipVertically);
        } else if (mBitmap != null) {
          bitmapSampled =
                  BitmapUtils.cropBitmapObjectHandleOOM(
                          mBitmap,
                          mCropPoints,
                          mDegreesRotated,
                          mFixAspectRatio,
                          mAspectRatioX,
                          mAspectRatioY,
                          mFlipHorizontally,
                          mFlipVertically);
        } else {
          return new Result((Bitmap) null, 1);
        }

        Bitmap bitmap =
                BitmapUtils.resizeBitmap(bitmapSampled.bitmap, mReqWidth, mReqHeight, mReqSizeOptions);

        if (mSaveUri == null) {
          return new Result(bitmap, bitmapSampled.sampleSize);
        } else {
          BitmapUtils.writeBitmapToUri(
                  mContext, bitmap, mSaveUri, mSaveCompressFormat, mSaveCompressQuality);
          if (bitmap != null) {
            bitmap.recycle();
          }
          return new Result(mSaveUri, bitmapSampled.sampleSize);
        }
      }
      return null;
    } catch (Exception e) {
      return new Result(e, mSaveUri != null);
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
          cropImageView.onImageCroppingAsyncComplete(paramResult);
        } 
      } 
      if (!bool1 && paramResult.bitmap != null)
        paramResult.bitmap.recycle(); 
    } 
  }
  
  static final class Result {
    public final Bitmap bitmap;
    
    final Exception error;
    
    final boolean isSave;
    
    final int sampleSize;
    
    public final Uri uri;
    
    Result(Bitmap param1Bitmap, int param1Int) {
      this.bitmap = param1Bitmap;
      this.uri = null;
      this.error = null;
      this.isSave = false;
      this.sampleSize = param1Int;
    }
    
    Result(Uri param1Uri, int param1Int) {
      this.bitmap = null;
      this.uri = param1Uri;
      this.error = null;
      this.isSave = true;
      this.sampleSize = param1Int;
    }
    
    Result(Exception param1Exception, boolean param1Boolean) {
      this.bitmap = null;
      this.uri = null;
      this.error = param1Exception;
      this.isSave = param1Boolean;
      this.sampleSize = 1;
    }
  }
}


/* Location:              C:\Users\DucMH\Desktop\dex-tools-v2.4\classes-dex2jar.jar!\com\alldoucment\reader\viewer\widget\cropper\BitmapCroppingWorkerTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */