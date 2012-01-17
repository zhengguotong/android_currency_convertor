package i11114675.zheng.currencyConvertor.utils;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class FlipperAnimation {
	/**
	 * sets right flip in amimation
	 * @return
	 */
	public static Animation inFromRightAnimation() {

    	Animation inFromRight = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  +1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	inFromRight.setDuration(350);
    	inFromRight.setInterpolator(new AccelerateInterpolator());
    	return inFromRight;
    }
	/**
	 * sets left flip out animation 
	 * @return
	 */
    public static Animation outToLeftAnimation() {
    	Animation outtoLeft = new TranslateAnimation(
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  -1.0f,
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	outtoLeft.setDuration(350);
    	outtoLeft.setInterpolator(new AccelerateInterpolator());
    	return outtoLeft;
    }   
    
    /**
     * sets left flip in animation 
     * @return
     */
    public static Animation inFromLeftAnimation() {
    	Animation inFromLeft = new TranslateAnimation(
    	Animation.RELATIVE_TO_PARENT,  -1.0f, Animation.RELATIVE_TO_PARENT,  0.0f,
    	Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	inFromLeft.setDuration(350);
    	inFromLeft.setInterpolator(new AccelerateInterpolator());
    	return inFromLeft;
    }
    
    /**
     * sets right flip out animation 
     * @return
     */
    public static Animation outToRightAnimation() {
    	Animation outtoRight = new TranslateAnimation(
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,  +1.0f,
    	 Animation.RELATIVE_TO_PARENT,  0.0f, Animation.RELATIVE_TO_PARENT,   0.0f
    	);
    	outtoRight.setDuration(350);
    	outtoRight.setInterpolator(new AccelerateInterpolator());
    	return outtoRight;
    }
}
