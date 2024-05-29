/*
 * Author: Jacob Hall-Burns
 * Email: jacob.hallburns02@gmail.com
 * Project Title: Calculator
 * Description: Calculator project to refamiliarize myself with C++ and learn basic GUI.
 */

#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int calculator () {
    string line;
    ifstream inputFile ("calcIntake1.txt");
    if (inputFile.is_open()) {
        while (getline (inputFile, line) ) {
            cout << line << '\n';
        }
        inputFile.close();
    } else {
        cerr << "Unable to open file" << endl;
    }
    return 0;
}