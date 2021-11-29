package allen.frame;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.adapter.CommonAdapter;
import allen.frame.adapter.ViewHolder;
import allen.frame.entry.BasicParams;
import allen.frame.entry.FileInfo;
import allen.frame.tools.FileIntent;
import allen.frame.tools.FileUtils;
import allen.frame.tools.MaterialUtil;
import allen.frame.tools.TimeUtil;
import allen.frame.widget.FluidLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static allen.frame.FileSelectorActivity.EXTRA_SELECT_CHOICE;
import static allen.frame.FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE;
import static allen.frame.FileSelectorActivity.EXTRA_SELECT_CHOICE_TYPE_NAME;
import static allen.frame.FileSelectorActivity.EXTRA_SELECT_COUNT;
import static allen.frame.FileSelectorActivity.EXTRA_SELECT_MODE;
import static allen.frame.FileSelectorActivity.MODE_SINGLE;
import static allen.frame.FileSelectorActivity.TYPE_FILE;
import static allen.frame.FileSelectorActivity.TYPE_FOLDER;

public class FileSelectorFragment extends BaseFragment {

    @BindView(R2.id.selector)
    RecyclerView selector;
    @BindView(R2.id.gen_layout)
    FluidLayout genLayout;
    private CommonAdapter<FileInfo> adapter;
    private Callback mCallback;
    private ArrayList<FileInfo> currentFileList = new ArrayList<>();
    private String path;
    private int mode = MODE_SINGLE;
    private int index = 0;
    private List<FileInfo> list;
    private boolean choice = false;
    private int type = TYPE_FOLDER;
    private FileInfo.FileType typeName = null;
    private int count = 0;
    private Map<Integer,Boolean> select;

    @Override
    protected int getLayoutResID() {
        return R.layout.alen_file_selector;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (Callback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("The Activity must implement FileSelectorFragment.Callback interface...");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        choice = getArguments().getBoolean(EXTRA_SELECT_CHOICE,false);
        mode = getArguments().getInt(EXTRA_SELECT_MODE,MODE_SINGLE);
        type = getArguments().getInt(EXTRA_SELECT_CHOICE_TYPE,TYPE_FOLDER);
        typeName = (FileInfo.FileType) getArguments().getSerializable(EXTRA_SELECT_CHOICE_TYPE_NAME);
        count = getArguments().getInt(EXTRA_SELECT_COUNT,0);
        initUI(view);
        addEvent(view);
    }

    private void initUI(View view) {
        list = new ArrayList<>();
        select = new HashMap<>();
        FileInfo gen = new FileInfo();
        gen.setFileName("根目录");
        gen.setFileType(FileInfo.FileType.Parent);
        gen.setFilePath(BasicParams.BasicPath);
        gen.setFileCount(-1);
        list.add(gen);
        setGenLayout(index);
        path = BasicParams.BasicPath;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        selector.setLayoutManager(manager);
        adapter = new CommonAdapter<FileInfo>(getActivity(), R.layout.alen_file_item) {
            @Override
            public void convert(ViewHolder holder, FileInfo entity, final int position) {
                holder.setText(R.id.FileName, entity.getFileName());
                holder.setText(R.id.FileCount, entity.getFileCount());
                holder.setText(R.id.FileDate, entity.getFileLastUpdateTime());
                holder.setOnClickListener(R.id.select_box, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setSelect(position);
                    }
                });
                if (entity.getFileType() == FileInfo.FileType.Parent) {
                    holder.setImageResource(R.id.FileIcon, R.drawable.allen_back_to_parent);
                    holder.setVisible(R.id.FileInfo, false);
                    holder.setVisible(R.id.select_box, false);
                    holder.setGravity(R.id.FileName, Gravity.CENTER_VERTICAL);
                } else if (entity.getFileType() == FileInfo.FileType.Folder) {
                    holder.setImageResource(R.id.FileIcon, R.mipmap.allen_file_folder);
                    holder.setVisible(R.id.FileInfo, true);
                    if(choice){
                        holder.setVisible(R.id.select_box, type==TYPE_FOLDER);
                        holder.setChecked(R.id.select_box,select.get(position));
                    }else{
                        holder.setVisible(R.id.select_box, false);
                    }
                } else if (entity.getFileType() == FileInfo.FileType.Audio) {
                    holder.setImageResource(R.id.FileIcon, R.mipmap.allen_file_audio);
                    holder.setVisible(R.id.FileInfo, true);
                    if(choice){
                        holder.setVisible(R.id.select_box, type==TYPE_FILE&&(typeName==FileInfo.FileType.Audio||typeName==FileInfo.FileType.Unknown));
                        holder.setChecked(R.id.select_box,select.get(position));
                    }else{
                        holder.setVisible(R.id.select_box, false);
                    }
                } else if (entity.getFileType() == FileInfo.FileType.Image) {
                    holder.setImageResource(R.id.FileIcon, R.mipmap.allen_file_image);
                    holder.setVisible(R.id.FileInfo, true);
                    if(choice){
                        holder.setVisible(R.id.select_box, type==TYPE_FILE&&(typeName==FileInfo.FileType.Image||typeName==FileInfo.FileType.Unknown));
                        holder.setChecked(R.id.select_box,select.get(position));
                    }else{
                        holder.setVisible(R.id.select_box, false);
                    }
                } else if (entity.getFileType() == FileInfo.FileType.Video) {
                    holder.setImageResource(R.id.FileIcon, R.mipmap.allen_file_video);
                    holder.setVisible(R.id.FileInfo, true);
                    if(choice){
                        holder.setVisible(R.id.select_box, type==TYPE_FILE&&(typeName==FileInfo.FileType.Video||typeName==FileInfo.FileType.Unknown));
                        holder.setChecked(R.id.select_box,select.get(position));
                    }else{
                        holder.setVisible(R.id.select_box, false);
                    }
                } else if (entity.getFileType() == FileInfo.FileType.Unknown) {
                    holder.setImageResource(R.id.FileIcon, R.mipmap.allen_file_unknown);
                    holder.setVisible(R.id.FileInfo, true);
                    if(choice){
                        holder.setVisible(R.id.select_box, type==TYPE_FILE&&typeName==FileInfo.FileType.Unknown);
                        holder.setChecked(R.id.select_box,select.get(position));
                    }else{
                        holder.setVisible(R.id.select_box, false);
                    }
                }
            }
        };
        selector.setAdapter(adapter);
        getFileList(path);
    }

    private void setSelect(int index){
        int have = 0;
        for(int i=0;i<select.size();i++){
            boolean check = select.get(i);
            if(check){
                have+=1;
            }
            if(mode==MODE_SINGLE){
                select.put(i,i==index);
            }else{
                if(i!=index){
                    select.put(i,check);
                }else{
                    if(check){
                        select.put(i,false);
                    }else{
                        if(count<=1||have<count){
                            select.put(i,true);
                        }
                    }
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void cancel(){
        for(int i=0;i<select.size();i++){
            select.put(i,false);
        }
        adapter.notifyDataSetChanged();
    }

    public ArrayList<String> getChoiceFiles(){
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<currentFileList.size();i++){
            if(select.get(i)){
                list.add(currentFileList.get(i).getFilePath());
            }
        }
        return list;
    }

    private void setGenLayout(int mindex){
        List<FileInfo> temp = new ArrayList<>();
        for(int i=0;i<=mindex;i++){
            temp.add(list.get(i));
        }
        list.clear();
        list.addAll(temp);
        genLayout.removeAllViews();
        for(int i=0;i<list.size();i++){
            final int k = i;
            AppCompatTextView item = new AppCompatTextView(getActivity());
            final FileInfo entry = list.get(i);
            item.setText(entry.getFileName()+" >");
            item.setTextColor(Color.GRAY);
            item.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.frame_text_normal_size));
            int padding = MaterialUtil.dip2px(getActivity(),8);
            int margin = MaterialUtil.dip2px(getActivity(),4);
            item.setPaddingRelative(padding,padding,padding,padding);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFileList(entry.getFilePath());
                    index = k;
                    setGenLayout(index);
                }
            });
            FluidLayout.LayoutParams params = new FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin,margin,margin,margin);
            genLayout.addView(item,params);
        }
    }

    private void addEvent(View view) {
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                FileInfo.FileType type = currentFileList.get(position).getFileType();
                if (type == FileInfo.FileType.Parent || type == FileInfo.FileType.Folder) {
                    if(type == FileInfo.FileType.Folder){
                        index += 1;
                        FileInfo cur = currentFileList.get(position);
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(cur.getFileName());
                        fileInfo.setFileLastUpdateTime("");
                        fileInfo.setFileType(FileInfo.FileType.Parent);
                        fileInfo.setFilePath(cur.getFilePath());
                        list.add(fileInfo);
                        setGenLayout(index);
                    }else{
                        index -=1;
                        setGenLayout(index);
                    }
                    getFileList(currentFileList.get(position).getFilePath());
                }else{
                    FileIntent.openFile(getActivity(),new File(currentFileList.get(position).getFilePath()));
                }
            }

            @Override
            public boolean onItemLongClick(View view, ViewHolder holder, int position) {
                return false;
            }

        });
    }

    /**
     * Callback for host activity
     */
    public interface Callback {
    }

    public void back(){
        if(index==0){
            getActivity().finish();
        }else {
            index-=1;
            setGenLayout(index);
            getFileList(list.get(index).getFilePath());
        }
    }



    private void getFileList(String Path) {
        select = new HashMap<>();
        currentFileList.clear();
        int sort = 0;
        File initFile = new File(Path);
        if (!Path.equals(BasicParams.BasicPath)) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName("返回上一级");
            fileInfo.setFileLastUpdateTime("");
            fileInfo.setFileType(FileInfo.FileType.Parent);
            fileInfo.setFilePath(initFile.getParent());
            fileInfo.setFileCount(-1);
            currentFileList.add(fileInfo);
            select.put(sort,false);
            sort+=1;
        }
        File[] files = initFile.listFiles();
        assert files != null;
        List<File> file_list = Arrays.asList(files);
        FileUtils.SortFilesByName(file_list);
        for (File f : file_list) {
            if (f.getName().indexOf(".") != 0) {
                select.put(sort,false);
                sort+=1;
                //隐藏文件不显示
                if (f.isDirectory()) {
                    //文件夹
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setFileName(f.getName());
                    fileInfo.setFileLastUpdateTime(TimeUtil.getDateInString(new Date(f.lastModified())));
                    fileInfo.setFileType(FileInfo.FileType.Folder);
                    fileInfo.setFilePath(f.getPath());
                    fileInfo.setFileCount(FileUtils.getSubfolderNum(f.getPath()));
                    Log.d("myfile", fileInfo.getFileName() + ":" + fileInfo.getFileCount());
                    currentFileList.add(fileInfo);
                } else {
                    if (FileUtils.isAudioFileType(f.getPath())) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(f.getName());
                        fileInfo.setFileLastUpdateTime(TimeUtil.getDateInString(new Date(f.lastModified())));
                        fileInfo.setFilePath(f.getPath());
                        fileInfo.setFileType(FileInfo.FileType.Audio);
                        fileInfo.setFileCount(f.length());
                        Log.d("myfile", fileInfo.getFileName() + ":" + fileInfo.getFileCount());
                        currentFileList.add(fileInfo);
                    } else if (FileUtils.isImageFileType(f.getPath())) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(f.getName());
                        fileInfo.setFileLastUpdateTime(TimeUtil.getDateInString(new Date(f.lastModified())));
                        fileInfo.setFilePath(f.getPath());
                        fileInfo.setFileType(FileInfo.FileType.Image);
                        fileInfo.setFileCount(f.length());
                        Log.d("myfile", fileInfo.getFileName() + ":" + fileInfo.getFileCount());
                        currentFileList.add(fileInfo);
                    } else if (FileUtils.isVideoFileType(f.getPath())) {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(f.getName());
                        fileInfo.setFileLastUpdateTime(TimeUtil.getDateInString(new Date(f.lastModified())));
                        fileInfo.setFilePath(f.getPath());
                        fileInfo.setFileType(FileInfo.FileType.Video);
                        fileInfo.setFileCount(f.length());
                        Log.d("myfile", fileInfo.getFileName() + ":" + fileInfo.getFileCount());
                        currentFileList.add(fileInfo);
                    } else {
                        FileInfo fileInfo = new FileInfo();
                        fileInfo.setFileName(f.getName());
                        fileInfo.setFileLastUpdateTime(TimeUtil.getDateInString(new Date(f.lastModified())));
                        fileInfo.setFilePath(f.getPath());
                        fileInfo.setFileType(FileInfo.FileType.Unknown);
                        fileInfo.setFileCount(f.length());
                        Log.d("myfile", fileInfo.getFileName() + ":" + fileInfo.getFileCount());
                        currentFileList.add(fileInfo);
                    }
                }
            }
        }
        adapter.setDatas(currentFileList);
    }
}
