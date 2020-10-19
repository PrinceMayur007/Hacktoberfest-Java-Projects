from selenium import webdriver
import os
import time

import configparser

#automate  #os based op #keep track of time

class InstagramBot:

    def __init__(self, username, password):
        """
        Author:
        @MayurNarkhede

        Initailizes an instance of the Instagram class.

        Call the login method to authenticate a user with Veronika.

        Args:
            username:str: The Instagram username for a user
            password:str: The Instagram password for a user 

        Attributes:
            driver:Selenium.webdriver.Chrome: The Chromedriver that is used to automate browser actions.
        """

        self.username = username
        self.password = password

        self.driver = webdriver.Chrome('chromedriver.exe')

        self.base_url = 'https://www.instagram.com'

        self.login()

    def login(self):
        self.driver.get('{}/accounts/login'.format(self.base_url))

        #copied Xpath from chrome inspector for username field of insta
        #self.driver.find_element_by_xpath('//*[@id="loginForm"]/div/div[1]/div/label/input')
        self.driver.implicitly_wait(20)     #if there is delay in loading, you can add this, this fix error of unable to locate element
        #self.driver.find_element_by_name('username').send_keys(self.username)
        self.driver.find_element_by_xpath('//*[@id="loginForm"]/div/div[1]/div/label/input').send_keys(self.password)
        self.driver.find_element_by_xpath('//*[@id="loginForm"]/div/div[2]/div/label/input').send_keys(self.password)
        #self.driver.find_element_by_name('password').send_keys(self.password)
        #time.sleep(1) works same as implicilty wait
        #self.driver.find_element_by_xpath("//div[contains(test(), 'Log In')]").click()
        self.driver.find_element_by_xpath('//*[@id="loginForm"]/div[1]/div[3]/button').click()

        #time.sleep(2)
        #abovesngle line yet to check

    def nav_user(self, user):
        self.driver.get('{}/{}/'.format(self.base_url, user))


    def follow_user(self, user):
        self.nav_user(user)
        follow_button = self.driver.find_element_by_xpath('//*[@id="react-root"]/section/main/div/header/section/div[2]/div/div/div/a/button')
        #if above not work, you can use below code line, same for unfollow you can create this.
        #follow_button = self.driver.find_elements_by_xpath("//button[contains(text(), 'Follow')]")[0]
        follow_button.click()







if __name__ == '__main__':
    

    config_path = './config.ini'
    #created a new file in folder config.ini
    cparser = configparser.ConfigParser()
    cparser.read(config_path)
    username = cparser['AUTH']['USERNAME']
    password = cparser['AUTH']['PASSWORD']

    veronika = InstagramBot(username, password)
    veronika.nav_user('iammayurn')
    veronika.follow_user('iammayurn')


