<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
***
***
***
*** To avoid retyping too much info. Do a search and replace for the following:
*** Klowar, iot_2021, twitter_handle, email, project_title, project_description
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/Klowar/iot_2021">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Raspberry Radio</h3>

  <p align="center">
    Just funny project to play with NFC, MQTT, Android, Raspberry, Python3
    <br />
    <a href="https://github.com/Klowar/iot_2021"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/Klowar/iot_2021">View Demo</a>
    ·
    <a href="https://github.com/Klowar/iot_2021/issues">Report Bug</a>
    ·
    <a href="https://github.com/Klowar/iot_2021/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

Radio by MQTT


### Built With

* [Python 3]()
* [MQTT]()
* [Android]()
* [Node 14]()



<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.
Take a note, if you do not have music list - we have to turn off our music provider server
so you must up your own, server sources in server folder

### Prerequisites

You should copy this repo only to raspberry pi, beacause of specific Python packages in it
For some reasons MQTT topics store on NFC, so you should have NFC with your topic name
* RFID rc522 board connected to your Raspberry
* npm
  ```sh
  sudo apt install python-alsaaudio gstreamer-1.0
  ```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Klowar/iot_2021.git
   ```
2. Install NPM packages
   ```sh
   cd iot_2021 && pip3 install -r raspberry/requirements.txt
   ```



<!-- USAGE EXAMPLES -->
## Usage

1. Run following command on Raspberry
   ```sh
   python3 raspberry/index.py
   ```

2. Send music from Android app


_For more examples, please refer to the [Documentation](https://github.com/Klowar/iot_2021/wiki)


<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/Klowar/iot_2021/issues) for a list of proposed features (and known issues).



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Project Link: [https://github.com/Klowar/iot_2021](https://github.com/Klowar/iot_2021)




<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Klowar/iot_2021.svg?style=for-the-badge
[contributors-url]: https://github.com/Klowar/iot_2021/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Klowar/iot_2021.svg?style=for-the-badge
[forks-url]: https://github.com/Klowar/iot_2021/network/members
[stars-shield]: https://img.shields.io/github/stars/Klowar/iot_2021.svg?style=for-the-badge
[stars-url]: https://github.com/Klowar/iot_2021/stargazers
[issues-shield]: https://img.shields.io/github/issues/Klowar/iot_2021.svg?style=for-the-badge
[issues-url]: https://github.com/Klowar/iot_2021/issues
[license-shield]: https://img.shields.io/github/license/Klowar/iot_2021.svg?style=for-the-badge
[license-url]: https://github.com/Klowar/iot_2021/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/Klowar
