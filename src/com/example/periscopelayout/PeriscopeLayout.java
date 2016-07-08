package com.example.periscopelayout;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.nfcdemo.R;
 
public class PeriscopeLayout extends RelativeLayout {
 
    private Interpolator line = new LinearInterpolator();// ����
    private Interpolator acc = new AccelerateInterpolator();// ����
    private Interpolator dce = new DecelerateInterpolator();// ����
    private Interpolator accdec = new AccelerateDecelerateInterpolator();// �ȼ��ٺ����
    private Interpolator[] interpolators;
 
    private int mHeight;
    private int mWidth;
    private LayoutParams lp;
    private Drawable[] drawables;
    private Random random = new Random();
 
    private int dHeight;
    private int dWidth;
 
    public PeriscopeLayout(Context context) {
        super(context);
        init();
    }
 
    public PeriscopeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
 
    public PeriscopeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
 
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PeriscopeLayout(Context context, AttributeSet attrs,
            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
 
    private void init() {
 
        // ��ʼ����ʾ��ͼƬ
        drawables = new Drawable[3];
        Drawable red = getResources().getDrawable(R.drawable.pl_1);
        Drawable yellow = getResources().getDrawable(R.drawable.pl_2);
        Drawable blue = getResources().getDrawable(R.drawable.pl_3);
 
        drawables[0] = red;
        drawables[1] = yellow;
        drawables[2] = blue;
        // ��ȡͼ�Ŀ�� ���ں���ļ���
        // ע�� ������3��ͼƬ�Ĵ�С����һ����,������ֻȡ��һ��
        dHeight = red.getIntrinsicHeight();
        dWidth = red.getIntrinsicWidth();
 
        // �ײ� ���� ˮƽ����
        lp = new LayoutParams(dWidth, dHeight);
        lp.addRule(CENTER_HORIZONTAL, TRUE);// �����TRUE Ҫע�� ����true
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);
 
        // ��ʼ���岹��
        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;
 
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }
 
    public void addHeart() {
 
        ImageView imageView = new ImageView(getContext());
        // ���ѡһ��
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        imageView.setLayoutParams(lp);
 
        addView(imageView);
 
        Animator set = getAnimator(imageView);
        set.addListener(new AnimEndListener(imageView));
        set.start();
 
    }
 
    private Animator getAnimator(View target) {
        AnimatorSet set = getEnterAnimtor(target);
 
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);
 
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(interpolators[random.nextInt(4)]);
        finalSet.setTarget(target);
        return finalSet;
    }
 
    private AnimatorSet getEnterAnimtor(final View target) {
 
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f,
                1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X,
                0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y,
                0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }
 
    private ValueAnimator getBezierValueAnimator(View target) {
 
        // ��ʼ��һ��������������- - ����
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2),
                getPointF(1));
 
        // ������û���ͼ ���һ�� ��������� �� �յ�
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF(
                (mWidth - dWidth) / 2, mHeight - dHeight),
                new PointF(random.nextInt(getWidth()), 0));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }
 
    /**
     * ��ȡ�м������ ��
     *
     * @param scale
     */
    private PointF getPointF(int scale) {
 
        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth - 100));// ��ȥ100 ��Ϊ�˿��� x����Χ,��Ч�� ����~~
        // ��Y���� Ϊ��ȷ���ڶ����� �ڵ�һ����֮��,�Ұ�Y�ֳ����������� ��������Ч����һЩ Ҳ��������������
        pointF.y = random.nextInt((mHeight - 100)) / scale;
        return pointF;
    }
 
    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {
 
        private View target;
 
        public BezierListenr(View target) {
            this.target = target;
        }
 
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            // �����ȡ�����������߼�������ĵ�x yֵ ��ֵ��view ���������ð���������������
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // ����˳����һ��alpha����
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }
 
    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;
 
        public AnimEndListener(View target) {
            this.target = target;
        }
 
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            // ��Ϊ��ͣ��add ������view����ֻ������,������view����������remove��
            removeView((target));
        }
    }
}
