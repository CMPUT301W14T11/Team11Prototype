package Model;

public class IDModel {
       private int id_for_master;
       //private int id_for_sub_comment;
       // constructor for id object
       public IDModel(int idMaster)
       {
    	   this.id_for_master=idMaster;
       }
	public int getId_for_master() {
		return id_for_master;
	}
	/*
	public void setId_for_master(int id_for_master) {
		this.id_for_master = id_for_master;
	}
	/*
	public int getId_for_sub_comment() {
		return id_for_sub_comment;
	}
	public void setId_for_sub_comment(int id_for_sub_comment) {
		this.id_for_sub_comment = id_for_sub_comment;
	}*/
     
}
