# 📚 WordNest - Dictionary App

WordNest is a modern dictionary app built using **Kotlin** and **Jetpack Compose**. It fetches definitions, phonetics, and example usages for English words using the [Free Dictionary API](https://dictionaryapi.dev/), all wrapped in a clean, responsive, and minimal UI.

---

## ✨ Features

- 🔍 Search for any English word
- 📖 View definitions, parts of speech, and example usage
- 🖌️ Beautiful and responsive UI with Jetpack Compose
- ⚙️ Built with MVVM and Clean Architecture
- 🧠 Uses Hilt for dependency injection
- 🌐 Retrofit integration for network calls
- 🚀 Lightweight, fast, and user-friendly

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVVM Architecture**
- **Retrofit**
- **Hilt (DI)**
- **Kotlin Coroutines & Flows**
- **Material Design 3**

---

import os
import time
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from getpass import getpass
import shutil

# ---------------------- USER INPUT ----------------------
username = input("Enter your username: ").strip()
password = getpass("Enter your password: ").strip()
user_code = input("Enter your code (e.g., qwerty00): ").strip()
base_folder = input("Enter the path where folders should be created: ").strip()  # user-defined path

# Ensure folder exists
os.makedirs(base_folder, exist_ok=True)

# ---------------------- SELENIUM SETUP ----------------------
download_dir = os.path.join(os.getcwd(), "downloads")
os.makedirs(download_dir, exist_ok=True)

chrome_options = Options()
chrome_options.add_experimental_option("prefs", {
    "download.default_directory": download_dir,
    "download.prompt_for_download": False,
    "directory_upgrade": True
})
chrome_options.add_argument("--headless")  # remove if you want browser visible
driver = webdriver.Chrome(options=chrome_options)

# ---------------------- LOGIN ----------------------
driver.get("https://example.com/login")  # replace with actual login URL

# Locate username/password fields and login button (adjust selectors)
driver.find_element(By.ID, "username").send_keys(username)
driver.find_element(By.ID, "password").send_keys(password)
driver.find_element(By.ID, "loginBtn").click()
time.sleep(5)  # wait for login to complete

# ---------------------- SEARCH & DOWNLOAD ----------------------
codes_to_search = {
    "usercode": f"{user_code}.stf",
    "pingpong": f"{user_code}.stf",  # replace if format differs
    "e1boot": f"{user_code}.frd"
}

downloaded_files = {}

for code_name, code_file in codes_to_search.items():
    # Navigate/search page (adjust according to site)
    search_box = driver.find_element(By.ID, "searchBox")  # adjust selector
    search_box.clear()
    search_box.send_keys(code_file)
    driver.find_element(By.ID, "searchBtn").click()
    time.sleep(3)  # wait for search results

    # Locate download link/button
    download_link = driver.find_element(By.LINK_TEXT, code_file)  # adjust selector
    download_link.click()
    print(f"Downloading {code_file}...")
    downloaded_files[code_name] = os.path.join(download_dir, code_file)
    time.sleep(5)  # wait for download

driver.quit()

# ---------------------- CREATE FOLDERS ----------------------
def remove_X8H(name):
    return name.replace("X8H", "")

user_folder = os.path.join(base_folder, remove_X8H(codes_to_search["usercode"]))
pingpong_folder = os.path.join(base_folder, remove_X8H(codes_to_search["pingpong"]))

os.makedirs(user_folder, exist_ok=True)
os.makedirs(pingpong_folder, exist_ok=True)

# Move files to folders
shutil.move(downloaded_files["usercode"], user_folder)
shutil.move(downloaded_files["e1boot"], user_folder)  # add e1 bootcode in user folder
shutil.move(downloaded_files["pingpong"], pingpong_folder)

print("Files downloaded and organized successfully!")

