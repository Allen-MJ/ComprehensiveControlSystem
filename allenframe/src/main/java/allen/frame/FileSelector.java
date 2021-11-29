package allen.frame;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

import allen.frame.entry.FileInfo;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.PermissionUtil;
import androidx.fragment.app.Fragment;

public class FileSelector {
    private static FileSelector selector;
    private boolean choice = false;
    private String title;
    private int mode = FileSelectorActivity.MODE_SINGLE;
    private int type = FileSelectorActivity.TYPE_FOLDER;
    private int count = 0;
    private FileInfo.FileType typeName = FileInfo.FileType.Unknown;
    private ArrayList<String> files;

    private FileSelector(){}
    public static FileSelector creat(){
        if(selector==null){
            selector = new FileSelector();
        }
        return selector;
    }

    public FileSelector setTitle(String title){
        this.title = title;
        return this;
    }

    public FileSelector canChoice(boolean choice){
        this.choice = choice;
        return this;
    }

    public FileSelector setChoiceType(int type, FileInfo.FileType typeName){
        this.type = type;
        this.typeName = typeName;
        return this;
    }
    public FileSelector setMode(int mode){
        this.mode = mode;
        return this;
    }

    public FileSelector setCount(int count){
        this.count = count;
        return this;
    }

    public FileSelector origin(ArrayList<String> files){
        this.files = files;
        return this;
    }


    public FileSelector start(Activity activity, int requestCode){
        if(PermissionUtil.isStoragePermissionGranted(activity)){
            Intent intent = new Intent(activity, FileSelectorActivity.class);
            intent.putExtra(FileSelectorActivity.EXTRA_SELECT_TITLE,title)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE,choice)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE,type)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE_NAME,typeName)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_MODE,mode)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_FILES,files)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_COUNT,count);
            activity.startActivityForResult(intent,requestCode);
        }else{
            MsgUtils.showMDMessage(activity, activity.getString(R.string.mis_error_no_permission));
        }
        return this;
    }
    public FileSelector start(Activity activity){
        if(PermissionUtil.isStoragePermissionGranted(activity)){
            Intent intent = new Intent(activity, FileSelectorActivity.class);
            intent.putExtra(FileSelectorActivity.EXTRA_SELECT_TITLE,title)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE,choice)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE,type)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_MODE,mode)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_FILES,files)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_COUNT,count);
            activity.startActivity(intent);
        }else{
            MsgUtils.showMDMessage(activity, activity.getString(R.string.mis_error_no_permission));
        }
        return this;
    }

    public FileSelector start(Fragment fragment, int requestCode){
        if(PermissionUtil.isStoragePermissionGranted(fragment.getActivity())){
            Intent intent = new Intent(fragment.getActivity(), FileSelectorActivity.class);
            intent.putExtra(FileSelectorActivity.EXTRA_SELECT_TITLE,title)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE,choice)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE,type)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE_NAME,typeName)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_MODE,mode)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_FILES,files)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_COUNT,count);
            fragment.startActivityForResult(intent,requestCode);
        }else{
            MsgUtils.showMDMessage(fragment.getActivity(), fragment.getString(R.string.mis_error_no_permission));
        }
        return this;
    }
    public FileSelector start(Fragment fragment){
        if(PermissionUtil.isStoragePermissionGranted(fragment.getActivity())){
            Intent intent = new Intent(fragment.getActivity(), FileSelectorActivity.class);
            intent.putExtra(FileSelectorActivity.EXTRA_SELECT_TITLE,title)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE,choice)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE,type)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_MODE,mode)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_FILES,files)
            .putExtra(FileSelectorActivity.EXTRA_SELECT_COUNT,count);
            fragment.startActivity(intent);
        }else{
            MsgUtils.showMDMessage(fragment.getActivity(), fragment.getString(R.string.mis_error_no_permission));
        }
        return this;
    }
}
