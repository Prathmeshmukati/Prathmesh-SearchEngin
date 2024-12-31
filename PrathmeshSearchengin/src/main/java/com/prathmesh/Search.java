package com.prathmesh;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/Search")

public class Search extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//            getting keyword from frontend
        String keyword = request.getParameter("keyword");

//            setting up connection to database
     Connection connection = DatabaseConnection.getConnection();


        try {
//         getting results after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select pagetitle, pagelink , (length(lower(pagetext))-length(replace(lower(pagetext),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "') as countoccurence from pagesS order by countoccurence  desc limit 30;");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            // transferrering values from resultset to results arraylist
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setPageTitle(resultSet.getString("pageTitle"));
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

//         displaying results arraylist in console
            for(SearchResult result:results){
                System.out.println(result.getPageTitle()+"\n"+result.getPageLink()+"\n");
            }
            request.setAttribute("results" , results);
            request.getRequestDispatcher("search.jsp").forward(request,response);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch (SQLException | ServletException sqlException){
            sqlException.printStackTrace();
        }
    }
}