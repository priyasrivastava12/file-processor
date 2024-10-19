package com.file.matcher.controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class NumberodIslands {

  public static void main(String args[]){

    int grid[][] = {
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0}};

    int number=findsocities(grid);


  }

  public static int findsocities(int[][] grid){



    Queue<int[]> queue=new LinkedList<>();
    Set<int[]> visited=new HashSet<>();

    int r=grid.length; //5
    int c= grid[0].length; //5
    int n=0;
    for(int i=0;i<r;i++){
      for(int j=0;j<c;j++){
        if(grid[i][j]==1) queue.add(new int[]{i,j}); // {}
      }

      while(!queue.isEmpty()){
        int size=queue.size();
        for(int m=0 ;m< size ;m++){
          int x=queue.peek()[0];
          int y=queue.peek()[1];

          if(visited.contains(int[]{x,y})){
            continue;
          }
            else{
            visited.add(int[]{x,y});
          }


          if(x>0 && grid[x-1][y]==1) {
            if(isVisited(visited,x-1,y))
              queue.add(int[]{x-1,y});
          }
          if(y>0 && grid[x][y-1]==1 ) {
            if(isVisited(visited,x,y-1))
              queue.add(int[]{x,y-1});

          }
          if(x<r-1 && grid[x+1][y]==1) {
            if(isVisited(visited,x+1,y))
              queue.add(int[]{x+1,y});
          }
          if(y<c-1 && grid[x][y+1]==1 ) {
            if(isVisited(visited,x,y+1))
              queue.add(int[]{x,y+1});
          }

          n++;



        }
      }

      return n;



    }

    public boolean isVisited(Set<int[]> visited,int x, int y){
      if(visited.contains(int[]{x,y})){
        return false;
      }
            else{
        return true;
      }


    }



  }
}
