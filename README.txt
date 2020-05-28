*	Author: Liam Johnston
*	Date late updated: 8.21.19
*	Purpose: a basic framework in which to experiment with linear algebric AI
*
*	User Notes: the program is started by running the java file
*	On running you should find multiple buttons along with an image in the background, this is the menu
*	There are multiple buttons but only start program and quit program are properly implenented
*	Program Options is an attempt to allow for some simple alterations with the settings eg player input in cleaner and more user friendly experience
*		such a window would allow for clicking various buttons to quickly re - key each button as the user inputs and allow for updating to same base settings with a range
*		for example to allow the option to select 1 or 2 players as the program is currently only setup to handle those number of players
*	Program Quit ends the program, in the windows environment using the close button (the X on the top right) serves the same purpose
*	Direct Connect is a button that literally does nothing at time of writing but was an idea of impleneting direct connection amongst computers to allow players to play on different computers
*	
*	Play / Start Program
*	This will actually 'hide' the menu and create a new window to begint the simulation
*	Unless other wise overriden there are som inbuilt keys to alter the simulation in both key and helpful ways, one example is that the '-' and '=' keys are used to decrease/ increase respectively the internal game speed.
*	This means that the program can be left to run at X times the frame speed which allows it to be left to calculate very quickly
*	'p' - pauses the simulation and depending on the item may remove some items from being drawn until the program is unpaused due to lack of proper implentation by programmer
*	't' - triggers the repopulation of the pellets, this is used if you are bored, testing or still having issues coorectly handling the rounds
*	eventually the program should automatically after X minutes reset the pellet board but at time of writing was not impleneted due to programmer sillyness in calculating the total frames passed and finding the remaininder when calculating for a round passed
*
*	Within the GameSettings.txt you can edit various settings with the following notes:
*	The program has inbuilt defaults so don't worry if you delete something
*
*	The player keyboard values can be changed but they are using the number values of the keyboards and would probably require a google search
*		at time of creation i found https://www.oreilly.com/library/view/javascript-dhtml/9780596514082/apb.html as a reasonable reference but not inherently trustworthy / the only reference
*
*	Finally the class / 'program'that reads the various file will ignore any line containing the asterick '*'
*	it will look for a certain pre built values to override and if it can't find them the program will default to prebuilt values
*	This means that even if something isn't commented out that the program will still functionally ignore it
*	The only other real syntax to follow is the 'variable name' '=' 'value' format which can have spaces anywhere it must have an '=' to distinguish the variable from the value
* 	eg 'pa us eKey = 83' works but ' pause.Key = eighty - three' really doesn't

* Actual final note: the actual program code could use more indepth programming notes