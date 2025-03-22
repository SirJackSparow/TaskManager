# 🏗️ Android Modular App - MVI, Hilt, Room, Compose

This is a modern **Android app** built with **Jetpack Compose, Hilt, Room, DataStore, Material3, and MVI architecture**.  
It follows a **modular approach** to ensure **scalability, testability, and reusability**.

## 🚀 Features

✅ **Jetpack Compose** for UI  
✅ **Hilt** for Dependency Injection  
✅ **Room** for local database storage  
✅ **DataStore** for lightweight data persistence  
✅ **Material3** for modern UI styling  
✅ **MVI Architecture** for state management  
✅ **Modularization** for better separation of concerns  

---

## 🏛️ **Architecture & Design Pattern - MVI**

This app follows the **MVI (Model-View-Intent) pattern**, ensuring:

- **Unidirectional Data Flow** for better state management.
- **Separation of Concerns** between UI, business logic, and data.
- **Immutable UI State** using `StateFlow` / `LiveData`.

### **MVI Breakdown:**
1️⃣ **View** 🖼️ → Renders UI and listens to state changes  
2️⃣ **Intent** 🎯 → User actions that trigger updates  
3️⃣ **Model** 🧠 → Handles business logic and updates UI state  

---

## 🧩 **Modularization Structure**

The app is split into **reusable modules**:


