
MarioKark AI

(The code was meant for learning and for fun, so its not very well commented.)

This is a Mario Kart 2D recreation with agents controlled by a Neural Network. (The Neural Network source code is in the Neural Network folder)

The training is done by "genetically" modifying the neural network, with agents who score higher having a more favorable chance of passing their Neural Network "genes".

The agents inform themselvesc by multiple rays in front of them which indicate the distance of nearby walls(in the ray's direction) and also the agents speed is inputed into its first layer of the NN.

The neural network outputs are : Speed up, do Nothing, Slow down, and Turn right, Turn Left or do Nothing. (Use boost - to be implemented)

To see the results, download the jar (or run kart/main/TestKart - main) and place the MarioKartMNNF file in your desktop.

(The "setup" is the code used by most of my programs as a library to start my canvas and basic draw() and tick() loops (Both run N times per second as specified in the constructor. The tick method is method that upates the state of the game). All the main classes implement the Manager interface and then start the Window in the main function)

![Animation2](https://user-images.githubusercontent.com/86021222/152413801-7f88d220-f123-4b84-97f1-4e12577c022b.gif)

Visualization of the Karts vision:

![kart2](https://user-images.githubusercontent.com/86021222/152414152-26050265-4f99-4306-a2f4-6537919ce212.png)


