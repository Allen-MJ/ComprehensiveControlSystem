package allen.frame.tools;

public abstract class UpdateInterface{
	public void judgeUpdateMethod(AppDialog dialog){};
	public void downLoad(AppDialog dialog){};
	public void downLoad(AppDialog dialog,String name,String url){};
	public void appMethod(AppDialog dialog){};
	public void openFile(String path,String msg){};
	public void downLoadFail(String msg){};
}