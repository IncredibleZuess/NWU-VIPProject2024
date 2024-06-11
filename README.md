## App User Guide
![example workflow](https://github.com/IncredibleZuess/NWU-VIPProject2024/actions/workflows/gradle.yml/badge.svg)
![Static Badge](https://img.shields.io/badge/Project_Leader-Anneme_Janse_Van_Rensburg-blue)
![Static Badge](https://img.shields.io/badge/Frontend_Developer-Liam_Craven-teal)
![Static Badge](https://img.shields.io/badge/Frontend_Developer-Stefan_Fourie-teal)
![Static Badge](https://img.shields.io/badge/Backend_Developer-Carlo_Barnardo-purple)
![Static Badge](https://img.shields.io/badge/Backend_Developer-Sebastian_Klopper-purple)


### Overview

This app helps parents monitor and manage the usage of selected apps by their children. It includes features for profile management, authentication, and blocking app usage based on set parameters(To be Implemented).

### Table of Contents
1. [Sign Up Procedure](#sign-up-procedure)
2. [Profile Management](#profile-management)
3. [App Blocking](#app-blocking)
4. [Gameplay to Gain More Time](#gameplay-to-gain-more-time)
5. [Settings](#settings)
6. [Home Page](#home-page)
7. [Navigation](#navigation)

### Sign Up Procedure

1. **Open the app.**
2. **Go to the sign-up page.**
3. **Enter your Name**
    - This will be the name displayed on the app.
4. **Generate your "Parent" Pin**
    - For now, use "000000" as the default pin. Future versions will include proper pin generation and storage.
5. **Proceed to complete Sign-Up.**

### Profile Management

The profile management feature allows parents to customize settings and manage their profiles.

1. **Access Profile:**
    - Go to the Profile section from the navigation bar.

2. **Customization:**
    - Change settings such as app cosmetics (background, font, etc.) in future updates.

3. **About Button:**
    - Provides information about the app.

4. **Dark Mode:**
    - Toggle dark mode using the switch provided in the profile settings.

### App Blocking(To be implemented in future release)

The app blocking service helps in restricting app usage based on predefined limits set by parents.

1. **Setting Up App Blocking:**
    - Parents can select up to 5 apps they wish to monitor and manage.

2. **Setting Time Limits:**
    - Set the time limit for each app.
    - Users will receive a notification 5 minutes before the time is up to allow saving any work.
    - Once the time is up, users will be locked out of the app until they earn more time or until midnight (00:00 local time).

3. **Foreground Service:**
    - The app runs a foreground service to ensure the app blocking functionality is active and uninterrupted.

### Gameplay to Gain More Time

1. **Start at the Home Page.**
2. **Navigate to the "Games" page.**
3. **Select the game category of your choice.**
4. **Choose your desired difficulty level.**
5. **Deduct time by successfully completing the game.**

### Settings

The settings menu is divided into two main categories:

1. **Default Settings:**
    - Customize app cosmetics such as background, font, etc. (subject to implementation).

2. **Parent Settings:**
    - Option to clear all saved data.

### Home Page

1. **Welcome Message:**
    - A greeting for the user.

2. **Usage Summary:**
    - A view showing app usage for the day.

### Navigation

1. **Anchored Bar:**
    - Switch between the Home Page, Settings Page, and Game Page easily.
