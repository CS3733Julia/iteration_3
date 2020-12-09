package com.julia.db;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.julia.model.Alternative;
import com.julia.model.Choice;
import com.julia.model.Feedback;
import com.julia.model.Member;

public class JuliaDAO {
	java.sql.Connection conn;

    public JuliaDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    public boolean checkAvailability(String idChoice, String username, String password) throws Exception {
    	try {
    		PreparedStatement psM = conn.prepareStatement("SELECT * FROM Member WHERE idChoice=?;");
            psM.setString(1,  idChoice);
            ResultSet resultSetMember = psM.executeQuery();
            
            int index = 0; 
            while (resultSetMember.next()) {           	
            	String usr = resultSetMember.getString("username");
            	String psw = resultSetMember.getString("password");
            	if(usr.equals(username) && psw.equals(password)) {
            		return true;
            	}
            	index ++;
            }
            
            Choice selectedChoice = getChoice(idChoice);
            
            if (selectedChoice == null) {
            	return false;
            }
            
            if(index < selectedChoice.maxParticipants) {
            	if(createMember(idChoice, username, password)){return true;}
            	else{return false;}
            }else {return false;}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
            throw new Exception("Failed at checking for Members: " + e.getMessage());    	}
    }
    
    //This function is for testing only- will need to be improved if used for lambda functions
    public Member getMember(String idChoice, String username, String password) throws Exception {
    	try {
    		PreparedStatement psM = conn.prepareStatement("SELECT * FROM Member WHERE (idChoice=? AND username=? AND password=?);");
            psM.setString(1, idChoice);
            psM.setString(2, username);
            psM.setString(3, password);
            ResultSet resultSetMember = psM.executeQuery();
            Member member = null;
            
            while(resultSetMember.next()) {
            	member = generateMember(resultSetMember);
            }
            
    		return member;
    	}catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting member: " + e.getMessage());
        } 	
    }
    
    //This function is for testing only- will need to be improved if used for lambda functions
    public Member getMemberById(String idMember) throws Exception {
    	try {
    		PreparedStatement psM = conn.prepareStatement("SELECT * FROM Member WHERE idMember=?;");
            psM.setString(1, idMember);
            ResultSet resultSetMember = psM.executeQuery();
            Member member = null;
            
            while(resultSetMember.next()) {
            	member = generateMember(resultSetMember);
            }
            
    		return member;
    	}catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting member: " + e.getMessage());
        } 	
    }
    
    public boolean createMember(String idChoice, String username, String password) throws Exception {
    	try {
    		Member newRecruit = new Member(username, password);
        	PreparedStatement psM  = conn.prepareStatement("INSERT INTO Member (idChoice,idMember,username,password) VALUES(?,?,?,?);");
    	    psM.setString(1, idChoice);
    	    psM.setString(2, newRecruit.idMember);
    	    psM.setString(3, newRecruit.username);
    	    psM.setString(4, newRecruit.getPassword());
             
            psM.execute();
            psM.close();
            return true;
    	}catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to create member: " + e.getMessage());
        }
    }
    
    public boolean createApproval(String idAlternative, String idMember) throws Exception {
    	try {
        	PreparedStatement psA  = conn.prepareStatement("INSERT INTO Approved (idAlternative,idMember) VALUES(?,?);");
    	    psA.setString(1, idAlternative);
    	    psA.setString(2, idMember);
             
            psA.execute();
            psA.close();
            return true;
    	}catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to create approval: " + e.getMessage());
        }
    }
    
    public boolean createDisapproval(String idAlternative, String idMember) throws Exception {
    	try {
        	PreparedStatement psD  = conn.prepareStatement("INSERT INTO Disapproved (idAlternative,idMember) VALUES(?,?);");
    	    psD.setString(1, idAlternative);
    	    psD.setString(2, idMember);
             
            psD.execute();
            psD.close();
            return true;
    	} catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to create Disapproval: " + e.getMessage());
        }
    }
    
    public boolean addFeedback(Feedback feedback) throws Exception {
    	try {
        	PreparedStatement psF  = conn.prepareStatement("INSERT INTO Feedback (idChoice,idAlternative,idMember,descriptionFeedback,date) VALUES(?,?,?,?,?);");
    	    psF.setString(1, feedback.idChoice);
    	    psF.setString(2, feedback.idAlternative);
    	    psF.setString(3, feedback.idMember);
    	    psF.setString(4, feedback.description);
    	    psF.setString(5, feedback.date);
             
            psF.execute();
            psF.close();
            return true;
    	}catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to add feedback: " + e.getMessage());
        }
    }

    public Choice getChoice(String UUID) throws Exception {
        
        try {
        	Choice choice = null;
            PreparedStatement psC = conn.prepareStatement("SELECT * FROM " + "Choice" + " WHERE idChoice=?;");
            psC.setString(1,  UUID);
            ResultSet resultSetChoice = psC.executeQuery();
            
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM " + "Alternative" + " WHERE idChoice=?;");
            psA.setString(1,  UUID);
            ResultSet resultSetAlternative = psA.executeQuery();
            ArrayList<Alternative> listAlternatives = new ArrayList<Alternative>(5);
            while (resultSetAlternative.next()) {
            	listAlternatives.add(getAlternative(resultSetAlternative.getString("idAlternative")));
            }
            
            while (resultSetChoice.next()) {
            	choice = generateChoice(resultSetChoice, listAlternatives);
            }
            
            resultSetChoice.close();
            resultSetAlternative.close();
            psC.close();
            psA.close();
            if (choice == null) {
            	new Exception("Choice with id:" + UUID + " not found in database");  
            }
            return choice;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
    
    public Choice getChoicebyAlternative(String UUID) throws Exception {
        
        try {
        	Choice choice = null;
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM " + "Alternative" + " WHERE idAlternative=?;");
            psA.setString(1,  UUID);
            ResultSet resultSetidChoice = psA.executeQuery();
            
            while (resultSetidChoice.next()) {
            	choice = getChoice(resultSetidChoice.getString("idChoice"));
            }
            
            resultSetidChoice.close();
            psA.close();
            return choice;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
        }
    }
    
    public Alternative getAlternative(String UUID) throws Exception {
        
        try {
        	Alternative alternative = null;
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM " + "Alternative" + " WHERE idAlternative=?;");
            psA.setString(1,  UUID);
            ResultSet resultSetAlternative = psA.executeQuery();
            
            PreparedStatement psAp = conn.prepareStatement("SELECT * FROM " + "Approved" + " WHERE idAlternative=?;");
            psAp.setString(1,  UUID);
            ResultSet resultSetApproved = psAp.executeQuery();
            ArrayList<String> listApproved = new ArrayList<String>(5);
            
            PreparedStatement psDp = conn.prepareStatement("SELECT * FROM " + "Disapproved" + " WHERE idAlternative=?;");
            psDp.setString(1,  UUID);
            ResultSet resultSetDisapproved = psDp.executeQuery();
            ArrayList<String> listDisapproved = new ArrayList<String>(5);
            
            PreparedStatement psF = conn.prepareStatement("SELECT * FROM " + "Feedback" + " WHERE idAlternative=?;");
            psF.setString(1,  UUID);
            ResultSet resultSetFeedback= psF.executeQuery();
            ArrayList<Feedback> listFeedback = new ArrayList<Feedback>();
            
            while (resultSetApproved.next()) {
            	listApproved.add(getMemberById(resultSetApproved.getString("idMember")).username);
            }
            
            while (resultSetDisapproved.next()) {
            	listDisapproved.add(getMemberById(resultSetDisapproved.getString("idMember")).username);
            }
            
            while(resultSetFeedback.next()) {
            	listFeedback.add(generateFeedback(resultSetFeedback));
            }
            
            while (resultSetAlternative.next()) {
            	alternative = generateAlternative(resultSetAlternative, listApproved, listDisapproved, listFeedback);
            }
            
            resultSetAlternative.close();
            resultSetApproved.close();
            resultSetDisapproved.close();
            psA.close();
            psAp.close();           
            psDp.close();

            return alternative;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting alternative: " + e.getMessage());
        }
    }
    
    
    public boolean checkChoiceComplete(String idChoice) throws Exception {
        try {
    	PreparedStatement psC = conn.prepareStatement("SELECT dateComplete FROM Choice WHERE idChoice=?;");
        psC.setString(1,  idChoice);
        ResultSet resultSetChoice = psC.executeQuery();
        
        while (resultSetChoice.next()) {
        	if (resultSetChoice.getString("dateComplete") != null) {
        		return true;
        	}
        }
        
        return false;
        
        }catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to check Choice: " + e.getMessage());
        }	
    }
    
    public boolean checkApproval(String alternativeUUID, String memberUUID) throws Exception {
        
        try {
        	String member = null;
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM Approved WHERE idAlternative=? AND idMember=?;");
            psA.setString(1,  alternativeUUID);
            psA.setString(2,  memberUUID);
            ResultSet resultSetApproved = psA.executeQuery();
            
            while(resultSetApproved.next()) {
            	member = resultSetApproved.getString("idMember");
            }
            
            resultSetApproved.close();
            psA.close();
            
            if (member != null) {
            	return true;
            }
            else {
            	return false;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to find approval: " + e.getMessage());
        }
    }
    
    public boolean checkDisapproval(String alternativeUUID, String memberUUID) throws Exception {
        
        try {
        	String member = null;
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM " + "Disapproved" + " WHERE idAlternative=?" +" AND" + " idMember=?;");
            psA.setString(1,  alternativeUUID);
            psA.setString(2,  memberUUID);
            ResultSet resultSetDisapproved = psA.executeQuery();
            
            while(resultSetDisapproved.next()) {
            	member = resultSetDisapproved.getString("idMember");
            }
            
            resultSetDisapproved.close();
            psA.close();
            
            if (member != null) {
            	return true;
            }
            else {
            	return false;
            }

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed to find disapproval: " + e.getMessage());
        }
    }

    public boolean addChoice(Choice choice) throws Exception {
        try {
        	PreparedStatement psC = conn.prepareStatement("SELECT * FROM " + "Choice" + " WHERE idChoice=?;");
            psC.setString(1,  choice.idChoice);
            ResultSet resultSetChoice = psC.executeQuery();
            
            PreparedStatement psA = conn.prepareStatement("SELECT * FROM " + "Alternative" + " WHERE idChoice=?;");
            psA.setString(1,  choice.idChoice);
            ResultSet resultSetAlternative = psA.executeQuery();
            ArrayList<Alternative> listAlternatives = new ArrayList<Alternative>(5);
            while (resultSetAlternative.next()) {
            	String idAlternative = resultSetAlternative.getString("idAlternative");
            	listAlternatives.add(getAlternative(idAlternative));
            }
            
            while (resultSetChoice.next()) {
            	choice = generateChoice(resultSetChoice, listAlternatives);
            	resultSetChoice.close();
                resultSetAlternative.close();
                psC.close();
                psA.close();
            	return false;
            }

            psC = conn.prepareStatement("INSERT INTO Choice (idChoice,descriptionChoice,maxParticipants,dateCreate,dateComplete) VALUES(?,?,?,?,?);");
            psC.setString(1, choice.idChoice);
            psC.setString(2, choice.description);
            psC.setInt(3, choice.maxParticipants);
            psC.setObject(4, choice.dateCreate);
            psC.setObject(5, choice.dateComplete);
            
            psC.execute();

            psA = conn.prepareStatement("INSERT INTO Alternative (idAlternative,idChoice,descriptionAlternative,isChosen) VALUES(?,?,?,?);");            
            
            for (Alternative a : choice.alternatives) {
                psA.setString(1, a.idAlternative);
                psA.setString(2, choice.idChoice);
                psA.setString(3, a.descriptionAlternative);
                psA.setBoolean(4, a.isChosen);  
                psA.execute();
            }        
    
            psC.close();
            psA.close();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert constant: " + e.getMessage());
        }
    }
    
    public boolean deleteChoice(String idChoice) throws Exception{
    	try {
          PreparedStatement psC = conn.prepareStatement("DELETE FROM Choice WHERE idChoice = ?;");
          psC.setString(1, idChoice);
          int numChoicesAffected = psC.executeUpdate();
          psC.close();
          
          PreparedStatement psA = conn.prepareStatement("DELETE FROM Alternative WHERE idChoice = ?;");
          psA.setString(1, idChoice);
          int numAlternativesAffected = psA.executeUpdate();
          psA.close();
          
          return (numChoicesAffected > 1 && numAlternativesAffected > 2 && deleteMember(idChoice));

      } catch (Exception e) {
          throw new Exception("Failed to delete choice: " + e.getMessage());
      }
    }
    
    public boolean deleteApproval(String idAlternative, String idMember) throws Exception{
    	try {
          PreparedStatement psA = conn.prepareStatement("DELETE FROM Approved WHERE idAlternative = ? AND idMember = ?;");
          psA.setString(1, idAlternative);
          psA.setNString(2, idMember);
          int numAlternativesAffected = psA.executeUpdate();
          psA.close();
          
          return (numAlternativesAffected > 0);

      } catch (Exception e) {
          throw new Exception("Failed to delete approval: " + e.getMessage());
      }
    }
    
    public boolean deleteDisapproval(String idAlternative, String idMember) throws Exception{
    	try {
          PreparedStatement psA = conn.prepareStatement("DELETE FROM Disapproved WHERE idAlternative = ? AND idMember = ?;");
          psA.setString(1, idAlternative);
          psA.setNString(2, idMember);
          int numAlternativesAffected = psA.executeUpdate();
          psA.close();
          
          return (numAlternativesAffected > 0);

      } catch (Exception e) {
          throw new Exception("Failed to delete disapproval: " + e.getMessage());
      }
    }
    
    public boolean deleteMember(String idChoice) throws Exception{
    	try {
    		PreparedStatement psM = conn.prepareStatement("DELETE FROM Members WHERE idChoice = ?;");
            psM.setString(1, idChoice);
            int numMembersAffected = psM.executeUpdate();
            psM.close();
            return true;
    	}catch (Exception e) {
            throw new Exception("Failed to delete Member: " + e.getMessage());
        }	
    }
    private Choice generateChoice(ResultSet resultSet, ArrayList<Alternative> alternatives) throws Exception {
        String idChoice = resultSet.getString("idChoice");
    	String description  = resultSet.getString("descriptionChoice");
        int maxParticipants = resultSet.getInt("maxParticipants");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateCreate = ((Timestamp) resultSet.getObject("dateCreate")).toLocalDateTime();
        String formattedDateComplete = "";
        try {
            LocalDateTime dateComplete = ((Timestamp)resultSet.getObject("dateComplete")).toLocalDateTime();
    		formattedDateComplete = dateComplete.format(formatter);
        }catch (NullPointerException e) {
        	LocalDateTime dateComplete = null;
        }
        
		String formattedDateCreate = dateCreate.format(formatter);

        return new Choice (idChoice, description, alternatives, maxParticipants, formattedDateCreate, formattedDateComplete);
    }
    
    private Alternative generateAlternative(ResultSet resultSet, ArrayList<String>  approvedSet, ArrayList<String>  disapprovedSet, ArrayList<Feedback> feedback) throws Exception{
    	//TODO : add feedback and approvals- later iteration
    	String idAlternative = resultSet.getString("idAlternative");
    	String description = resultSet.getString("descriptionAlternative");
    	boolean isChosen = false;
    	if(resultSet.getInt("isChosen") == 1) { isChosen = true; }
    	
    	return new Alternative(idAlternative, description, isChosen, approvedSet, disapprovedSet, feedback);
    }
    
    private Member generateMember(ResultSet resultSet) throws Exception{
    	String idMember = resultSet.getString("idMember");
    	String username = resultSet.getString("username");
    	String password = resultSet.getString("password");
    	
    	return new Member(idMember, username, password);
    }
    
    private Feedback generateFeedback(ResultSet resultSet) throws Exception{
    	String idMember = resultSet.getString("idMember");
    	Member member = getMemberById(idMember);
    	String description = resultSet.getString("descriptionFeedback");
    	String date = resultSet.getString("date");
    	
    	return new Feedback(member.username, description, date);
    }
    
    // get a list of all choices in the Database
    public List<Choice> getAllChoices() throws Exception {
        
        List<Choice> allChoices = new ArrayList<>();
        try {
            Statement psC = conn.createStatement();
            String query = "SELECT * FROM Choice;";
            ResultSet resultSet = psC.executeQuery(query);

            while (resultSet.next()) {
                Choice c = generateChoice(resultSet);
                allChoices.add(c);
            }
            resultSet.close();
            psC.close();
            return allChoices;

        } catch (Exception e) {
            throw new Exception("Failed in getting choices: " + e.getMessage());
        }
    }
    
    // Helper function, the choice only needs id, dateCreate, and dateComplete for the admin page
    private Choice generateChoice(ResultSet resultSet) throws Exception {
        String idChoice  = resultSet.getString("idChoice");
        String dateCreate = resultSet.getString("dateCreate");
        String dateComplete = resultSet.getString("dateComplete");
        if (dateComplete == null) {
        	dateComplete = "Not Complete";
        }
  
        return new Choice(idChoice, null, null, 0, dateCreate, dateComplete);
    }
}
