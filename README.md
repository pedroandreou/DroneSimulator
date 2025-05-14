# 🚁 Drone Simulator

<div align="center">
  <img src="https://github.com/user-attachments/assets/b1566a12-1fd0-4754-bdf7-98cc3c486741" alt="Drone Simulator GUI Screenshot" width="500"/>
</div>

## 📰 Description
My console Drone simulator has been extended to GUI using JavaFX library. Through the application, the user must be able to interact with it by adding various types of Drones, Obstacles, loading or saving an arena and be able to read the games' information and exit the game.

## 🎮 Gameplay
The game features three types of drones with different abilities:
- **ThanosDrone** (Black & White): The strongest drone
- **HulkDrone** (Green): Medium strength
  - Cannot be destroyed by normal obstacles
  - Can eliminate fire obstacles
  - Bounces off normal obstacles
- **SuperDrone** (Blue): The weakest drone

### Obstacles
There are two types of obstacles:
1. **Normal Obstacles**: Static obstacles that:
   - Can transform ThanosDrone into HulkDrone
   - Can destroy SuperDrone
2. **Fire Obstacles**: Mobile obstacles that:
   - Can fly around the arena
   - Have same effects as normal obstacles
   - Can be eliminated by HulkDrone

<div align="center">
  <img src="https://github.com/user-attachments/assets/c4b5843d-989f-4a04-9905-92711ebd3ff6" alt="Drone Types and Obstacles" width="500"/>
</div>


## 📂 Project Structure
This repository includes two different projects:
- [Console Drone Simulator](https://github.com/pedroandreou/DroneSimulator/tree/main/DroneSimulator)
- [Drone Simulator GUI](https://github.com/pedroandreou/DroneSimulator/tree/main/DroneGUI)

## 🛠 Initialization & Setup
```bash
# Clone the repository
git clone https://github.com/pedroandreou/DroneSimulator.git
```

## 🚀 Building and Running
Run the [batch file](https://github.com/pedroandreou/DroneSimulator/blob/main/runnable_jar/run.bat) to start the application.

## 👤 Author
<p align="left">
  <a href="https://www.linkedin.com/in/petrosandreou80/">
    <img src="https://img.shields.io/badge/Petros_LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
  </a>
</p>
