
MarioKartMNN

(The code was meant for learning and for fun, so its not very well commented.)

This is a Mario Kart 2D recreation with agents controlled by a Neural Network. (The Neural Network source code is in the Neural Network repository)
(The setup is the code used by most of my programs as a library to start my canvas and basic draw() and tick() (or Update) loops. All the main classes implement the Manger class and then start the Window in the main function)

The training is done by "genetically" modifying the neural network,with agents who score higher having a more favorable chance of passing their Neural Network "genes".

The agents inform themselvesc by multiple rays in front of them which indicate the distance of nearby walls(in the ray's direction) and also the agents speed is inputed into its first layer of the NN.

The neural network outputs are : Speed up, do Nothing, Slow down, and Turn right, Turn Left or do Nothing. (Use boost - to be implemented)

To see the results, download the jar (or run TestKart) and place the MarioKartMNNF in your desktop.

![Animation2](https://user-images.githubusercontent.com/86021222/152413801-7f88d220-f123-4b84-97f1-4e12577c022b.gif)

Visualization of the Karts vision:

![kart2](https://user-images.githubusercontent.com/86021222/152414152-26050265-4f99-4306-a2f4-6537919ce212.png)


