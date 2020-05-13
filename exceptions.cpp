
/* Quadratic Equation Solver

v1 - Calculates roots, but does not check for a negative discriminant.

v2 - (For you to implement)
        Throw an exception when discriminant is
            negative.
        Catch exception and give feedback to user.
*/
#include <iostream>
#include <math.h>
#include <limits>
using namespace std;


void getCoefficients(double& a, double& b, double& c)
{
    do {
        if (!cin.good()) {
            cout << "Coefficients must be numbers." << endl << endl;
            // clean stream
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
        }

        cout << "Enter a, b and c" << endl;
        cin >> a;
        cin >> b;
        cin >> c;
    } while (!cin.good());
}

void calculateRoots(double a, double b, double c,
                    double& x1, double& x2)
{
    // calculate discriminant
    double disc = b * b - 4 * a * c;
    
        //negative discriminant
    if (disc < 0) {
		throw std::out_of_range("No real roots.");
    }
    else {
        // positive discriminant
        x1 = (-b + sqrt(disc)) / (2 * a);
        x2 = (-b - sqrt(disc)) / (2 * a);
    }
}

void outputRoots(double x1, double x2)
{
    cout << "x1 = " << x1 << endl;
    cout << "x2 = " << x2 << endl;
}

int main()
{
    // coefficients
    double a = 0;
    double b = 0;
    double c = 0;
    // roots
    double x1 = 0;
    double x2 = 0;

    char cont = 'y';
    
        while (cont == 'y') {
            try {
                getCoefficients(a, b, c);
                // Check if a is equal to 0
                if (a == 0) {
                    // if true output not a quad eq
                    cout << endl << "Not a quadratic equation." << endl;
                }   
                else {
                    calculateRoots(a, b, c, x1, x2);
                    outputRoots(x1, x2);
                }
            }
            catch (std::out_of_range &e) { 
                cout << "ERROR: " << e.what() << endl;
            }
            cout << endl << "Input 'y' or 'Y' to do another. Input anything else to exit." << endl;
            cin >> cont;
            cont = tolower(cont);
        }
}