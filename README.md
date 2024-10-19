https://medium.com/@priyasrivastava18official/files-matching-system-hld-a6c3242c5bce

Problem :
As per the problem , there are 2 files which is being provided

File 1
File 2
Do matching of these files based on certain criteria and datatype of columns provided .

Solution :

As the problem statement is very vague , you have to come up with the assumption before making sure that the HLD you are preparing is able to handle all the edge case scenerios .

ASSUMPTIONS :

We are taking an assumption that daily there are lot of request coming for matching of file1 and file2 by different user .
The other assumption is the file size is such that in memory matching is not an issue .(This solution can be extended to big file size as well using chunk process method)
As the request is high , we dont want the customer to wait very long time in order for their request to be processed , so we opting for the event-driven architecture .
If the request per day is very less , we can go for the batch-application approach .

<img width="1386" alt="Screenshot 2024-10-19 at 4 04 27 PM" src="https://github.com/user-attachments/assets/84aa6fe8-373e-4d0b-9e13-27d29374d70d">



