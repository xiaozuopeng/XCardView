package cn.xzp.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(21)
class CardViewApi21Impl extends CardViewApi17Impl {

    // 标记 - 是否使用低版本实现
    private boolean useLower = false;

    CardViewApi21Impl() {
    }

    @Override
    public void initialize(CardViewDelegate cardView, Context context, ColorStateList backgroundColor, float radius, float elevation, float maxElevation,
                           ColorStateList shadowColorStart, ColorStateList shadowColorEnd, float mTopDelta) {
        // 没有自定义阴影颜色，不使用低版本实现
        if (shadowColorStart == null && shadowColorEnd == null) {
            useLower = false;
            RoundRectDrawable background = new RoundRectDrawable(backgroundColor, radius);
            cardView.setCardBackground(background);
            View view = cardView.getCardView();
            view.setClipToOutline(true);
            view.setElevation(elevation);
            this.setMaxElevation(cardView, maxElevation);
        } else {
            // 配置了自定义颜色，使用低版本实现
            useLower = true;
            super.initialize(cardView, context, backgroundColor, radius, elevation, maxElevation, shadowColorStart, shadowColorEnd, mTopDelta);
        }
    }

    public void setRadius(CardViewDelegate cardView, float radius) {
        if (useLower) {
            super.setRadius(cardView, radius);
        } else {
            this.getCardBackground(cardView).setRadius(radius);
        }
    }

    public void setMaxElevation(CardViewDelegate cardView, float maxElevation) {
        if (useLower) {
            super.setMaxElevation(cardView, maxElevation);
        } else {
            this.getCardBackground(cardView).setPadding(maxElevation, cardView.getUseCompatPadding(), cardView.getPreventCornerOverlap());
            this.updatePadding(cardView);
        }
    }

    public float getMaxElevation(CardViewDelegate cardView) {
        if (useLower) {
            return super.getMaxElevation(cardView);
        } else {
            return this.getCardBackground(cardView).getPadding();
        }
    }

    public float getMinWidth(CardViewDelegate cardView) {
        if (useLower) {
            return super.getMinWidth(cardView);
        } else {
            return this.getRadius(cardView) * 2.0F;
        }
    }

    public float getMinHeight(CardViewDelegate cardView) {
        if (useLower) {
            return super.getMinHeight(cardView);
        } else {
            return this.getRadius(cardView) * 2.0F;
        }
    }

    public float getRadius(CardViewDelegate cardView) {
        if (useLower) {
            return super.getRadius(cardView);
        } else {
            return this.getCardBackground(cardView).getRadius();
        }
    }

    public void setElevation(CardViewDelegate cardView, float elevation) {
        if (useLower) {
            super.setElevation(cardView, elevation);
        } else {
            cardView.getCardView().setElevation(elevation);
        }
    }

    public float getElevation(CardViewDelegate cardView) {
        if (useLower) {
            return super.getElevation(cardView);
        } else {
            return cardView.getCardView().getElevation();
        }
    }

    public void updatePadding(CardViewDelegate cardView) {
        if (useLower) {
            super.updatePadding(cardView);
        } else {
            if (!cardView.getUseCompatPadding()) {
                cardView.setShadowPadding(0, 0, 0, 0);
            } else {
                float elevation = this.getMaxElevation(cardView);
                float radius = this.getRadius(cardView);
                int hPadding = (int) Math.ceil((double) RoundRectDrawableWithShadow.calculateHorizontalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
                int vPadding = (int) Math.ceil((double) RoundRectDrawableWithShadow.calculateVerticalPadding(elevation, radius, cardView.getPreventCornerOverlap()));
                cardView.setShadowPadding(hPadding, vPadding, hPadding, vPadding);
            }
        }
    }

    public void onCompatPaddingChanged(CardViewDelegate cardView) {
        if (useLower) {
            super.onCompatPaddingChanged(cardView);
        } else {
            this.setMaxElevation(cardView, this.getMaxElevation(cardView));
        }
    }

    public void onPreventCornerOverlapChanged(CardViewDelegate cardView) {
        if (useLower) {
            super.onPreventCornerOverlapChanged(cardView);
        } else {
            this.setMaxElevation(cardView, this.getMaxElevation(cardView));
        }
    }

    public void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color) {
        if (useLower) {
            super.setBackgroundColor(cardView, color);
        } else {
            this.getCardBackground(cardView).setColor(color);
        }
    }

    public ColorStateList getBackgroundColor(CardViewDelegate cardView) {
        if (useLower) {
            return super.getBackgroundColor(cardView);
        } else {
            return this.getCardBackground(cardView).getColor();
        }
    }

    private RoundRectDrawable getCardBackground(CardViewDelegate cardView) {
        return (RoundRectDrawable) cardView.getCardBackground();
    }
}
