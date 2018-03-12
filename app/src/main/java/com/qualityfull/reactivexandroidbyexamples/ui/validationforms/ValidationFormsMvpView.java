package com.qualityfull.reactivexandroidbyexamples.ui.validationforms;


import com.qualityfull.reactivexandroidbyexamples.ui.base.MvpView;

public interface ValidationFormsMvpView extends MvpView {

    void setEnabledButton(boolean enabled);

    void hideErrorEnabledFullName(boolean hide, int minSize, int maxSize);

    void hideErrorEnabledEmail(boolean hide);

    void hideErrorEnabledPassword(boolean hide);

    void setMaskDate(String masked);

    void setMaskPhone(String masked);

    void hideErrorEnabledDateBirth(boolean hide);

    void hideErrorEnabledPhone(boolean hide);

    void register();
}
