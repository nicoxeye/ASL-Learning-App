<a id="readme-top"></a>

<!-- https://github.com/othneildrew/Best-README-Template/blob/main/BLANK_README.md -->

<h1 align="center">$$\color{#a1585c}SignWise$$</h1>

  <p align="center">
    App with a focus of gamification of learning an American Sign Language, prioritizing engagement and motivation 😊
    <br />
    <br />
    <a href="https://github.com/nicoxeye/ASL-Learning-App">View Demo</a> <!-- TODO: ADD VIDEO OF FINISHED APP:D -->
    &middot;
    <a href="https://github.com/nicoxeye/ASL-Learning-App/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    &middot;
    <a href="https://github.com/nicoxeye/ASL-Learning-App/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>


<!-- ABOUT THE PROJECT -->
## 🌟 About The Project

With the increasing availability of technology, the number of available applications on the market is also growing. Despite this, there are currently **relatively few** applications supporting sign language learning. This led to the idea of ​​developing such software, inspired by well-known apps like Duolingo, which motivate users to learn through **experience points, user progress level, and various learning modes**. The project aims to increase the accessibility of sign language learning options and to influence motivation to practice through the use of **gamification** elements.

**SignWise** is an AI-powered educational app. The program we developed enables real-time feedback by implementing a classification model that utilizes a camera to determine whether the displayed sign is correct.

<!-- PROJECT SCREENSHOT HERE:D + DESCRIPTION OF FEATURES ETC -->

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## 👾 Built With

* [![Kotlin][Kotlin]][Kotlin-url]
* [![Android][Android]][Android-url]
* [![TensorFlow][TensorFlow]][TensorFlow-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## 🎯 Features
### **1. 🤴🏻 User account**

    - saved user progress (experience points -> level progression)
    - ability to change username
    - ability to reset experience progress
### **2. 📚 Learning mode**

    - scrolling list of alphabet or numbers that allows users to explore ASL signs before testing themselves,
      making it easier for beginners to understand and revisit how individual signs are performed
### **3. FOUR testing modes where you can get experience points:**

  **a) 🕹️ Quiz**
  
        - 3 categories to choose from: alphabet, numbers & mixed mode
        - sets of 10 questions with 4 answer options each
        - set completion pop-up displaying the score and options to replay with a new set or return to the main menu
   **b) 🃏 Flashcards**
  
        - 3 categories to choose from: alphabet, numbers & personal favourites - you decide which flashcards make the list
        - smooth animations (stack animation, swiping and flipping) make learning more dynamic and enjoyable
        - finish pop-up with learning statistics ("known" vs "still learning" flashcards) and navigation options:
            - back to the menu
            - "still learning" flashcards repetition
            - whole set repetition
   **c) 🧩 Match Game**
  
        - 3 categories to choose from: alphabet, numbers & mixed mode
        - time-based gameplay - beat your personal best!
        - smooth matching animations for better user experience
        - set completion pop-up showing completion time with options to play again using a new set or navigate back to the main menu
   **d) 🤖 AI Camera**
  
        - real-time ASL sign recognition using the device camera
        - AI Quiz:
            - the app prompts the user to perform a specific sign
            - live gesture analysis with instant correctness feedback
            - current prediction hint highlighting incorrect hand position or movement

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## 🛠️ Future enhancements
- [ ] difficulty levels in quiz & match game
- [ ] connecting the rest of the features with Room Database
- [ ] _expanding set database_ -> adding new categories such as greetings, cooking, traveling etc. including popular phrases like "Hello", "Thank you"
- [ ] _enhancing AI model_ -> e.g. adding Hand Landmarker for better hand recognition
- [ ] daily streaks
- [ ] achievements

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Project Lead Team ❤️

<a href="https://github.com/nicoxeye/ASL-Learning-App/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=nicoxeye/ASL-Learning-App" alt="contrib.rocks image" />
</a>



<!-- MARKDOWN LINKS & IMAGES -->

<!-- Shields.io badges. You can a comprehensive list with many more badges at: https://github.com/inttter/md-badges -->
[Kotlin]: https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white
[Kotlin-url]: https://kotlinlang.org
[Android]: https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white
[Android-url]: https://developer.android.com
[TensorFlow]: https://img.shields.io/badge/TensorFlow-ff8f00?logo=tensorflow&logoColor=white
[TensorFlow-url]: https://tensorflow.org
