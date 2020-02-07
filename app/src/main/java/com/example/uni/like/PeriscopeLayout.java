package com.example.uni.like;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.uni.R;

import java.util.Random;

public class PeriscopeLayout extends RelativeLayout {

    private int dHeight;  //爱心的高度
    private int dWidth;   // 爱心的宽度
    private int mHeight;  //自定义布局的高度
    private int mWidth;   //自定义布局的宽度

    private LayoutParams layoutParams;
    private Random random = new Random();  //用于获取随机心的随机数
    private Drawable[] drawables;  //存放初始化图片的数组

    // 我为了实现 变速效果 挑选了几种插补器
    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
    // 在init中初始化
    private Interpolator[] interpolators ;

    /**
     * 是在java代码创建视图的时候被调用，如果是从xml填充的视图，就不会调用这个
     */

    public PeriscopeLayout(Context context) {
        super(context);
        init();
    }

    /**
     * 这个是在xml创建但是没有指定style的时候被调用
     */
    public PeriscopeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 这个是在xml创建但是 有指定style的时候被调用
     */
    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化布局和随机心型图片
     */
    private void init() {

        //初始化显示的图片,暂时使用3 种图片
        drawables = new Drawable[3];

        //getResources().getDrawable 过期的替代方法 ContextCompat.getDrawable(getContext(),R.drawable.heart3);
//        Drawable red = getResources().getDrawable(R.drawable.heart3);
        Drawable red = ContextCompat.getDrawable(getContext(), R.drawable.logo_fly1);
        Drawable yellow = ContextCompat.getDrawable(getContext(), R.drawable.logo_fly2);
        Drawable blue = ContextCompat.getDrawable(getContext(), R.drawable.logo_fly3);

        drawables[0] = red;
        drawables[1] = yellow;
        drawables[2] = blue;

        //获取图的宽高 用于后面的计算
        //注意 我这里3张图片的大小都是一样的,所以我只取了一个
        dHeight = 160;//red.getIntrinsicHeight();
        dWidth = 160;//red.getIntrinsicWidth();

        //定义心型图片出现的位置，底部 水平居中
        layoutParams = new LayoutParams(dWidth, dHeight);
        layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        // 初始化插补器
        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;

    }

    /**
     * http://blog.csdn.net/pi9nc/article/details/18764863
     * 自定义布局 onMeasure 的作用
     * 获取控件的实际高度
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //注意!!  获取本身的宽高 需要在测量之后才有宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    /**
     * 通过属性动画 实现爱心图片的缩放和透明度变化的动画效果
     * target 就是爱心图片的view
     */
    private AnimatorSet getEnterAnimtor(final View target) {


        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);

        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());//线性变化
        enter.playTogether(scaleX, scaleY);
        enter.setTarget(target);

        return enter;
    }

    /**
     * 动画结束后，remove
     */
    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            //因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }
    /**
     * 提供外部实现点击效果，只有缩放和变淡的效果
     */
    public void addFavorWithoutBiz(){
        ImageView imageView = new ImageView(getContext());
        //随机心型颜色
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        imageView.setLayoutParams(layoutParams);

        addView(imageView);

        Animator set = getEnterAnimtor(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }
    /**
     * 贝塞尔曲线的动画实现
     */

    private ValueAnimator getBezierValueAnimator(View target) {
        //初始化一个BezierEvaluator
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));

        //第一个PointF传入的是初始点的位置
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF((mWidth - dWidth) / 2, mHeight - dHeight-20), new PointF(random.nextInt(getWidth()), 0));//随机
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    /**
     * 获取中间的两个点
     */
    private PointF getPointF(int scale) {

        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth - 50));//减去50 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt((mHeight - 150)) / scale;
        return pointF;
    }

    /**
     * 只有在回调里使用了计算的值,才能真正做到曲线运动
     */
    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {
        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);

            // alpha动画
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }





    private Animator getAnimator(View target){
        AnimatorSet set = getEnterAnimtor(target);

        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);

        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(interpolators[random.nextInt(4)]);//实现随机变速
        finalSet.setTarget(target);
        return finalSet;
    }

    /*
    *增加调用方法
    */
    public void addFavor() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        imageView.setLayoutParams(layoutParams);

        addView(imageView);

        Animator set = getAnimator(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();
    }
}
//引用https://blog.csdn.net/frimary/article/details/80826012