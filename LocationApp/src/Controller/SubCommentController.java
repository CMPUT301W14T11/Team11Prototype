package Controller;

public interface SubCommentController {
    // use this function get subcomments from server, and put the subcomment into the listview 
	public void get_subcomment(int master_comment_id);
	// upload subcomment to server
    public void upload_subcomment(); 
}
