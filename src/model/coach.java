package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class coach {

    // Player Attributes
    public enum coach_status{
        Active, Suspended, Retired
    }
    private int coach_ID;
    private String schoolCode;
    private String first_name;
    private String last_name;
    private LocalDate contract_start;
    private LocalDate contract_end;
    private double monthly_salary;
    private int career_championships;
    private coach_status coachStatus;


    //Constructor

    public coach ( int coach_ID, String schoolCode, String last_name,String first_name,  LocalDate contract_start, LocalDate contract_end, double monthly_salary, int career_championships, coach_status coachStatus) {
        this.coach_ID = coach_ID;
        this.schoolCode = schoolCode;
        this.first_name = first_name;
        this.last_name = last_name;
        this.contract_start = contract_start;
        this.contract_end = contract_end;
        this.monthly_salary = monthly_salary;
        this.career_championships = career_championships;
        this.coachStatus = coachStatus;
    }

    //getters

    public int getCoachID()
    {
        return coach_ID;
    }

    public String getSchoolCode()
    {
        return schoolCode;
    }
    public String getFirstName()
    {
        return first_name;
    }
    public String getLastName()
    {
        return last_name;
    }
    public LocalDate getContractStart()
    {
        return contract_start;
    }
    public LocalDate getContractEnd()
    {
        return contract_end;
    }
    public double getMonthlySalary()
    {
        return monthly_salary;
    }
    public int getCareerChampionships()
    {
        return career_championships;
    }
    public coach_status getCoachStatus()
    {
        return coachStatus;
    }
  
    // setters

    public void setCoachID(int coach_ID)
    {
        this.coach_ID = coach_ID;
    }

    public void setSchoolCode(String schoolCode)
    {
        this.schoolCode = schoolCode;
    }

    public void setFirstName(String first_name)
    {
        this.first_name = first_name;
    }
    
    public void setLastName(String last_name)
    {
        this.last_name = last_name;
    }
    public void setContractStart(LocalDate contract_start)
    {
        this.contract_start = contract_start;
    }

    public void setContractEnd(LocalDate contract_end)
    {
        this.contract_end = contract_end;
    }

    public void setMonthlySalary(double monthly_salary)
    {
        this.monthly_salary = monthly_salary;
    }

    public void setCareerChampionships(int career_championships)
    {
        this.career_championships = career_championships;
    }
    public void setCoachStatus(coach_status coachStatus)
    {
        this.coachStatus = coachStatus;
    }

    // display method for testing


    public void displayCoachInfo()
    {
        System.out.println("Coach ID:" + this.coach_ID + "| School Code: " + this.schoolCode + "| Last Name: " + this.last_name + "| First Name: " + this.first_name + "| Contract Start: " + this.contract_start + "| Contract End: " + this.contract_end + "| Monthly Salary: " + this.monthly_salary + "| Career Championships: " + this.career_championships + "| Coach Status: " + this.coachStatus);
    }
    // database connection

    private static final String DB_URL = System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/uaapstats");
    private static final String USER = System.getenv().getOrDefault("DB_USER","root");
    private static final String PASSWORD = System.getenv().getOrDefault("DB_PASSWORD",""); // insert ur local password here lol

    private static Connection conn = null;

    //database methods

   private static boolean connectDB()
   {
    try
    {
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        System.out.println("UAAP DB Connected Successfully.");
        return true;
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return false;
    }
   }

   public static List<coach> getAllCoaches()
   {
    List<coach> coaches = new ArrayList<>();

    try
    {
        if (conn == null || conn.isClosed())
        {
            if (!connectDB())
            {
                return coaches;
            }
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
        return coaches;
    }

    StringBuilder query = new StringBuilder("SELECT * FROM coach_record_management ");


    try (Statement stmt = conn.createStatement(); 
        ResultSet rs = stmt.executeQuery(query.toString()))
    {

        while(rs.next())
        {
            String statusStr = rs.getString("coach_status");
            coach.coach_status status = coach.coach_status.valueOf(statusStr);
            LocalDate contractStart = rs.getObject("contract_start",LocalDate.class);
            LocalDate contractEnd = rs.getObject("contract_end", LocalDate.class);

            coach c = new coach(
                rs.getInt("coach_ID"),
                rs.getString("schoolCode"),
                rs.getString("last_name"),
                rs.getString("first_name"),
                contractStart,
                contractEnd,
                rs.getDouble("monthly_salary"),
                rs.getInt("career_championships"),
                status
            );
            coaches.add(c);
        }
    }
    catch (SQLException e)
    {
        System.out.println(e.getMessage());
    }

    return coaches;

   }

   	public void coach_record_view(){
		String query = "SELECT * " +
		"FROM coach_record_management";

		try{
			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				System.out.println(rs.getString("schoolCode"));
				System.out.println(rs.getString("last_name"));
				System.out.println(rs.getString("first_name"));
				System.out.println(rs.getString("contract_start"));
				System.out.println(rs.getString("contract_end"));
				System.out.println(rs.getDouble("monthly_salary"));
				System.out.println(rs.getInt("career_championships"));
				System.out.println(rs.getString("coach_status"));
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}

	public boolean coach_record_insert(int coach_ID, String schoolCode, String last_name, String first_name, LocalDate contract_start, LocalDate contract_end, double monthly_salary, int career_championships, coach_status coachStatus)
	{

		String query = "INSERT INTO coach_record_management(coach_ID,  schoolCode,  last_name,  first_name,  contract_start,  contract_end,  monthly_salary,  career_championships,  coach_status) VALUES (?,?,?,?,?, ?,?,?,?)";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, coach_ID);
			pstmt.setString(2, schoolCode);
			pstmt.setString(3, last_name);
			pstmt.setString(4, first_name);
			pstmt.setDate(5, java.sql.Date.valueOf(contract_start));
			pstmt.setDate(6, java.sql.Date.valueOf(contract_end));
			pstmt.setDouble(7, monthly_salary);
			pstmt.setInt(8, career_championships);
			pstmt.setString(9, coachStatus.name());

            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public static boolean coach_record_delete(int coach_ID)
	{

		String query = "DELETE FROM coach_record_management WHERE coach_ID = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, coach_ID);
            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public boolean coach_record_update(String schoolCode, String last_name, String first_name, LocalDate contract_start, LocalDate contract_end, double monthly_salary, int career_championships, coach_status coachStatus)
	{
		String query = "UPDATE coach_record_management SET schoolCode = ?, last_name = ?, first_name = ? ,contract_start = ?, contract_end = ?, monthly_salary = ?, career_championships = ?, coach_status = ? WHERE coachID = ?";

		try
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, schoolCode);
			pstmt.setString(2, last_name);
			pstmt.setString(3, first_name);
			pstmt.setDate(4, java.sql.Date.valueOf(contract_start));
			pstmt.setDate(5, java.sql.Date.valueOf(contract_end));
			pstmt.setDouble(6, monthly_salary);
			pstmt.setInt(7, career_championships);
			pstmt.setString(8, coachStatus.name());
            pstmt.setInt(9, this.coach_ID);

            return pstmt.executeUpdate() > 0;


		} catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
	}

// main
   public static void main(String[] args)
   {
    connectDB();
    List<coach> coaches = getAllCoaches();

    for (coach c: coaches)
    {
       c.displayCoachInfo();
    }
   }


}

