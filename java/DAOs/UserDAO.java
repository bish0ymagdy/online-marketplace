package DAOs;

import java.sql.*;

import user.model.User;

import user.model.UserProfile;

public class UserDAO extends BaseDAO {

    public boolean registerUser(User user) {
        String insertUserSQL = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        
        String insertProfileSQL = "INSERT INTO user_profiles (user_id, first_name, last_name, phone_number, address, city, country) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement userStmt = conn.prepareStatement(insertUserSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement profileStmt = conn.prepareStatement(insertProfileSQL)) {
            
            try {
                conn.setAutoCommit(false);

                // Insert into users
                userStmt.setString(1, user.getUsername());
                userStmt.setString(2, user.getEmail());
                userStmt.setString(3, user.getPasswordHash());
                userStmt.executeUpdate();

                // Fetch generated ID
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedUserId = generatedKeys.getInt(1);
                        
                        // Insert into profiles
                        UserProfile profile = user.getProfile();
                        profileStmt.setInt(1, generatedUserId);
                        profileStmt.setString(2, profile.getFirstName());
                        profileStmt.setString(3, profile.getLastName());
                        profileStmt.setString(4, profile.getPhoneNumber());
                        profileStmt.setString(5, profile.getAddress());
                        profileStmt.setString(6, profile.getCity());
                        profileStmt.setString(7, profile.getCountry());
                        
                        profileStmt.executeUpdate();
                    } else {
                        throw new SQLException("Registration failed: Could not obtain generated user ID.");
                    }
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String email, String passwordHash) {
        
    	String loginSQL = "SELECT u.*, " +
                "p.id AS profile_id, " +
                "p.user_id AS profile_uid, " + 
                "p.first_name, p.last_name, p.phone_number, p.address, p.city, p.country " +
                "FROM users u " +
                "JOIN user_profiles p ON u.id = p.user_id " +
                "WHERE u.email = ? AND u.password_hash = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(loginSQL)) {
            
            stmt.setString(1, email);
            stmt.setString(2, passwordHash);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setStatus(rs.getString("status"));

                    UserProfile profile = new UserProfile();
                    profile.setId(rs.getInt("profile_id"));
                    profile.setUserId(rs.getInt("profile_uid"));
                    profile.setFirstName(rs.getString("first_name"));
                    profile.setLastName(rs.getString("last_name"));
                    profile.setPhoneNumber(rs.getString("phone_number"));
                    profile.setAddress(rs.getString("address"));
                    profile.setCity(rs.getString("city"));
                    profile.setCountry(rs.getString("country"));

                    user.setProfile(profile);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM user_profiles WHERE phone_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, phoneNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateUserProfile(UserProfile profile) {
        // 1. Write the SQL update statement targetting the user_id foreign key
        String updateSQL = "UPDATE user_profiles SET " +
                           "first_name = ?, " +
                           "last_name = ?, " +
                           "phone_number = ?, " +
                           "address = ?, " +
                           "city = ?, " +
                           "country = ? " +
                           "WHERE user_id = ?";

        // 2. Open resources safely using try-with-resources
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            
            // 3. Map the parameters from the incoming profile object
            stmt.setString(1, profile.getFirstName());
            stmt.setString(2, profile.getLastName());
            stmt.setString(3, profile.getPhoneNumber());
            stmt.setString(4, profile.getAddress());
            stmt.setString(5, profile.getCity());
            stmt.setString(6, profile.getCountry());
            
            // The foreign key dictates exactly whose profile row gets updated
            stmt.setInt(7, profile.getUserId()); 

            // 4. Execute the update statement
            int rowsAffected = stmt.executeUpdate();
            
            // Returns true if a row was successfully modified, false otherwise
            return rowsAffected > 0;

        } catch (SQLException e) {
            // Log the exception for tracing inside Eclipse console logs
            e.printStackTrace();
        }
        
        return false;
    }
}
