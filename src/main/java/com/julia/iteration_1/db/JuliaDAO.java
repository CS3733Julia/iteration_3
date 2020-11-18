package com.julia.iteration_1.db;

import java.sql.*;

import com.julia.iteration_1.model.Alternative;
import com.julia.iteration_1.model.Choice;
import com.julia.iteration_1.model.Member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    public String getMember(String idChoice, String username, String password) throws Exception {
    	try {
    		PreparedStatement psM = conn.prepareStatement("SELECT * FROM Member WHERE (idChoice=? AND username=? AND password=?);");
            psM.setString(1, idChoice);
            psM.setString(2, username);
            psM.setString(3, password);
            ResultSet resultSetMember = psM.executeQuery();
            resultSetMember.next();
    		return resultSetMember.getString("username");
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
            	listAlternatives.add(generateAlternative(resultSetAlternative));
            }
            
            while (resultSetChoice.next()) {
            	choice = generateChoice(resultSetChoice, listAlternatives);
            }
            
            resultSetChoice.close();
            resultSetAlternative.close();
            psC.close();
            psA.close();
            return choice;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting choice: " + e.getMessage());
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
            	listAlternatives.add(generateAlternative(resultSetAlternative));
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
    
    private Alternative generateAlternative(ResultSet resultSet) throws Exception{
    	//TODO : add feedback and approvals- later iteration
    	String idAlternative = resultSet.getString("idAlternative");
    	String description = resultSet.getString("descriptionAlternative");
    	boolean isChosen = false;
    	if(resultSet.getInt("isChosen") == 1) { isChosen = true; }
    	
    	return new Alternative(idAlternative, description, isChosen);
    }
}
