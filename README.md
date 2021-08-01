
MarioKartMNN(!PROJECT INCOMPLETE!)
(Sorry new to GITHUB)

(The code was meant for my training and for fun, so its not very commented.)

This is a Mario Kart 2D recreation with agents controlled by a Neural Network. (The Neural Network source code is in the Neural Network repository)

The training is done by (not complete) "genetically" modifying the neural network,with agents who score higher having a more favorable chance of passing their Neural Network "genes".

The agents inform themselvesc by multiple rays in front of them which indicate the distance of nearby walls(in the ray's direction) and also the agents speed is inputed into its first layer of the NN.

The neural network outputs are : Speed up, do Nothing, Slow down, and Turn right, Turn Left or do Nothing. (Use boost - to be implemented)

The Mapper serves to manually create the borders for the .png map images.

All the classes which are implemented from the entity class are used to make the borders for the road( With the mapper ).

If confused by Kart and NKart - Kart is the kart entity, NKart contains the neural network the control the kart.


![MarioKart](https://user-images.githubusercontent.com/86021222/127756541-5ccb3510-516f-4c66-83ea-259e1701dc91.png)
![Mapper](https://user-images.githubusercontent.com/86021222/127756549-194fd6af-4208-473f-a5fa-bdeebb9223cd.png)
