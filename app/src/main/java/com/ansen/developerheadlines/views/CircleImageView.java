package com.ansen.developerheadlines.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ansen.developerheadlines.R;

public class CircleImageView extends ImageView {

	private Bitmap mask;
	private Paint paint;
	
	private int borderColor=0xffffffff;
	private int roundWidth=0;
	private int roundHeight=0;
	private int borderWidth=0;

	private static final Xfermode MASK_XFERMODE;
	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public CircleImageView(Context paramContext) {
		super(paramContext);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	public CircleImageView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		this.setScaleType(ScaleType.CENTER_CROP);
	}

	private Bitmap makeDst(int w, int h) 
    {
        Bitmap bm = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#ffffffff"));   
        //c.drawOval(new RectF(0, 0, w, h), p);
        c.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), w, h, p);
        return bm;
    }

	@Override
	protected void onDraw(Canvas paramCanvas) {
		Drawable localDrawable = getDrawable();
		if (localDrawable == null)
			return;
		try {
			initPaint();
			//获取drawable的宽和高
			int dWidth = localDrawable.getIntrinsicWidth();
			int dHeight = localDrawable.getIntrinsicHeight();	

			//创建bitmap
//		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),Config.ARGB_8888);
			//创建画布
//			Canvas drawCanvas = new Canvas(bitmap);
			//缩放比例
			float scale = 1.0f;
			//按照bitmap的宽高，以及view的宽高，计算缩放比例；因为设置的src宽高比例可能和imageview的宽高比例不同，这里我们不希望图片失真；
//			if (dWidth == dHeight)
//			{
//				// 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
//				scale =  Math.max(getWidth() * 1.0f / dWidth, getHeight()
//						* 1.0f / dHeight);
//			} else
//			{
//				scale = getWidth() * 1.0F / Math.min(dWidth, dHeight);
//			}
			scale =  Math.max(getWidth() * 1.0f / dWidth, getHeight()
					* 1.0f / dHeight);
			//根据缩放比例，设置bounds，相当于缩放图片了
			localDrawable.setBounds(0, 0, (int) (scale * dWidth),
					(int) (scale * dHeight));
//			localDrawable.draw(drawCanvas);
			
			float f1 = getWidth();
			float f2 = getHeight();
			int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
//			localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				if(this.roundHeight==0&&this.roundWidth==0){
					this.roundHeight = getHeight();
					this.roundWidth = getWidth();
				}
				this.mask = makeDst(roundWidth, roundHeight);
			}
			paramCanvas.drawBitmap( this.mask, 0.0F, 0.0F, this.paint);
			drawBorder(paramCanvas, j, k);
			drawPercent(paramCanvas,paint);
			paramCanvas.restoreToCount(i);
			return;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	private void drawBorder(Canvas canvas, final int width, final int height) {
		if(this.borderWidth==0){
			return;
		}
		Paint mBorderPaint = new Paint();
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(this.borderColor);
		mBorderPaint.setStrokeWidth(borderWidth);
		int step = borderWidth/2;
		canvas.drawRoundRect(new RectF(0+step,step, getWidth()-step, getHeight()-step), width-borderWidth, height-borderWidth, mBorderPaint);
		/**
		 * 坐标x：view宽度的一般 坐标y：view高度的一般 半径r：因为是view的宽度-border的一半
		 */
		//canvas.drawCircle(width >> 1, height >> 1, (width - border) >> 1,	mBorderPaint);
	}
	
	public void setRound(int roundWidthDP,int roundHeightDP){
		this.roundHeight=roundHeightDP;
		this.roundWidth = roundWidthDP;
		
		float density = this.getContext().getResources().getDisplayMetrics().density;
		roundWidth = (int) (roundWidth*density);
		roundHeight = (int) (roundHeight*density);
	}
	/**
	* @Title: setBorderColor
	* @Description: TODO(设置边框颜色值)
	* @param @param color   argb值 设定文件
	* @return void    返回类型
	* @throws
	*/ 
	public void setBorder(int color,int widthPX){
		if(color==-1){
//			borderColor=0xffececec;
			borderColor=0xffffffff;
		}
		else{
			this.borderColor=color;
		}
		this.borderWidth = widthPX;
	}
	
	public void setBorder(int widthPX){
		borderColor=0xffececec;
		this.borderWidth = widthPX;
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		super.setColorFilter(cf);
		initPaint();
		paint.setColorFilter(cf);
	}
	
	private void initPaint(){
		if (this.paint == null) {
			Paint localPaint1 = new Paint();
			this.paint = localPaint1;
			this.paint.setFilterBitmap(false);
			Paint localPaint2 = this.paint;
			Xfermode localXfermode1 = MASK_XFERMODE;
			@SuppressWarnings("unused")
			Xfermode localXfermode2 = localPaint2
					.setXfermode(localXfermode1);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
    public enum Status {
        RUNNING, NONE
    }
    private static final int DEFAULT_TEXTCOLOT = 0xff0074a2;
    private static final int DEFAULT_TEXTSIZE = 32;
    private float mPercent;
    private int percent;
    private Bitmap mScaledBitmap;
    private float mLeft;
    private int mSpeed = 10;
    private int mRepeatCount = 0;
    private Status mFlag = Status.NONE;
    private int mTextColot = DEFAULT_TEXTCOLOT;
    private int mTextSize = DEFAULT_TEXTSIZE;
    public void setTextColor(int color) {
        mTextColot = color;
    }

    public void setTextSize(int size) {
        mTextSize = size;
    }

    public void setPercent(float percent) {
        mFlag = Status.RUNNING;
        mPercent = percent;
        this.percent = (int)(percent*100);
        postInvalidate();
    }

    public void setStatus(Status status) {
        mFlag = status;
    }

    public void clear() {
        mFlag = Status.NONE;
        if (mScaledBitmap != null) {
            mScaledBitmap.recycle();
            mScaledBitmap = null;
        }
        postInvalidate();
    }
    protected void drawPercent(Canvas canvas,Paint paint) {
        if (mFlag == Status.RUNNING) {
            if (mScaledBitmap == null) {
                Bitmap mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.percent_wave);
//                mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), getHeight(), false);
                mScaledBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth(), mBitmap.getHeight(), false);
                mBitmap.recycle();
                mBitmap = null;
                mRepeatCount = (int) Math.ceil(getWidth() / mScaledBitmap.getWidth() + 0.5) + 1;
            }

            for (int idx = 0; idx < mRepeatCount; idx++) {
                canvas.drawBitmap(mScaledBitmap, mLeft + (idx - 1) * mScaledBitmap.getWidth(), -mPercent * getHeight(), null);
            }
            if(percent<=100){
            	String str = percent + "%";
            	paint.setColor(mTextColot);
            	paint.setTextSize(mTextSize);
            	canvas.drawText(str, (getWidth() - paint.measureText(str)) / 2, getHeight() / 2 + mTextSize / 2, paint);
                
            	mLeft += mSpeed;
                if (mLeft >= mScaledBitmap.getWidth())
                    mLeft = 0;
                postInvalidateDelayed(30);
            }

        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	// TODO Auto-generated method stub
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    /**
	 * 绘制形状
	 * @return
	 */
	public Bitmap getBitmap()
	{
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);

		if (getWidth()!= getHeight())
		{
			canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
					roundWidth, roundHeight, paint);
		} else
		{
			canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2,
					paint);
		}

		return bitmap;
	}
}