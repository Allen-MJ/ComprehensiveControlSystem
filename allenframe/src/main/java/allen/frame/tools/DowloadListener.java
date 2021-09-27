package allen.frame.tools;

public abstract class DowloadListener {
	public void start(String path,long current,long total){};//开始下载
	public void progress(String path,long current,long total,String msg){};//下载中
	public void pause(String path,long finish,long total,String msg){};//暂停
	public void sucess(String path,long total,String msg){};//下载完成
	public void fail(String msg){};//下载失败
	public void historySucess(String path,String msg){};//已经下载完成的文件
}
