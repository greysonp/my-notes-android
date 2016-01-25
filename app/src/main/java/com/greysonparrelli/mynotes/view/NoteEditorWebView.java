package com.greysonparrelli.mynotes.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


public class NoteEditorWebView extends WebView {

    private IOnContentRetrievedListener mOnContentRetrievedListener;
    private IOnEditorEventListener mOnEditorEventListener;
    private boolean mLoadedWithoutListener;


    // =============================================
    // Overrides
    // =============================================

    public NoteEditorWebView(Context context) {
        super(context);
        init();
    }

    public NoteEditorWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    // =============================================
    // Public
    // =============================================

    public void sendCommand(Command command) {
        String commandString = command.getCommand() == null ? "null" : "'" + command.getCommand() + "'";
        String extra = command.getExtra()== null ? "null" : "'" + command.getExtra() + "'";

        loadUrl("javascript:inputCommand(" + commandString + ", " + extra + ")");
    }

    public void setContent(String content) {
        loadUrl("javascript:setContent('" + content + "')");
    }

    public void getContent(IOnContentRetrievedListener listener) {
        mOnContentRetrievedListener = listener;
        loadUrl("javascript:getContent()");
    }

    public void setOnEditorEventListener(IOnEditorEventListener listener) {
        mOnEditorEventListener = listener;
        if (mLoadedWithoutListener) {
            mOnEditorEventListener.onReady();
            mLoadedWithoutListener = false;
        }
    }


    // =============================================
    // Private
    // =============================================

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init() {
        WebView.setWebContentsDebuggingEnabled(true);
        getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new EditorJavascriptInterface(), "Android");
        loadUrl("file:///android_asset/note-editor.html");
    }


    // =============================================
    // Classes
    // =============================================

    public class EditorJavascriptInterface {

        @JavascriptInterface
        public void sendContent(String content) {
            if (mOnContentRetrievedListener != null) {
                mOnContentRetrievedListener.onContentRetrieved(content);
            }
        }

        @JavascriptInterface
        public void saveContent(String content) {
            if (mOnEditorEventListener != null) {
                mOnEditorEventListener.onContentShouldBeSaved(content);
            }
        }

        @JavascriptInterface
        public void setNotSynced() {
            if (mOnEditorEventListener != null) {
                mOnEditorEventListener.onNotSynced();
            }
        }

        @JavascriptInterface
        public void onReady() {
            if (mOnEditorEventListener != null) {
                mOnEditorEventListener.onReady();
            } else {
                mLoadedWithoutListener = true;
            }
        }
    }

    public interface IOnContentRetrievedListener {
        void onContentRetrieved(String content);
    }

    public interface IOnEditorEventListener {
        void onReady();
        void onContentShouldBeSaved(String content);
        void onNotSynced();
    }

    public enum Command {
        BOLD("bold", null),
        ITALIC("italic", null),
        UNDERLINE("underline", null),
        ORDERED_LIST("insertOrderedList", null),
        UNORDERED_LIST("insertUnorderedList", null),
        INDENT("indent", null),
        OUTDENT("outdent", null),
        HEADING("formatBlock", "H1"),
        LINE_BREAK("lineBreak", null);;

        private String mCommand;
        private String mExtra;

        Command(String command, String extra) {
            mCommand = command;
            mExtra = extra;
        }

        public String getCommand() {
            return mCommand;
        }

        public String getExtra() {
            return mExtra;
        }
    }
}
