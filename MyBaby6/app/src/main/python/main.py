from selenium import webdriver
import time

from selenium.webdriver import ActionChains
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys


#close the browser
def linkk(links, shop, adress):
    driver = webdriver.Chrome()
    driver.maximize_window()
    links = ['https://www.perekrestok.ru/cat/417/p/pelmeni-sibirskaa-kollekcia-russkie-kategoria-v-700g-3693336', 'https://www.perekrestok.ru/cat/370/p/moloko-ultrapasterizovannoe-domik-v-derevne-3-2-925ml-2059346']
    shop = 'Перекресток'
    adress = "г Москва, Петровско-Разумовская аллея, д 10 к 3"
    if shop == 'Перекресток':
        driver.get(links[0])
        button = driver.find_element(By.XPATH, "//button[@class='sc-eCstlR ftZPut cart-add-button']")
        driver.execute_script("arguments[0].click();", button)
        time.sleep(10)
        button2 = driver.find_element(By.XPATH, "//button[@class='sc-eCstlR djqBVS']")
        driver.execute_script("arguments[0].click();", button2)
        time.sleep(5)
        driver.find_element(By.XPATH, "//input[@id='react-select-2-input']").send_keys(adress)
        time.sleep(4)
        button3 = driver.find_element(By.XPATH, "(//button[@class='sc-eCstlR ftZPut delivery-status__submit'])[1]")
        driver.execute_script("arguments[0].click();", button3)
        for link in links[1:]:
            driver.get(link)
            button = driver.find_element(By.XPATH, "//button[@class='sc-eCstlR ftZPut cart-add-button']")
            driver.execute_script("arguments[0].click();", button)
            time.sleep(5)
        button = driver.find_element(By.XPATH, "//a[@class='sc-eCstlR djqBVS header__cart-button']//span[@class='button-icon']")
        driver.execute_script("arguments[0].click();", button)
        time.sleep(10)
        driver.close()

    if shop == 'Ашан':
        for link in links:
            driver.get(link)
            button = driver.find_element(By.XPATH, "//div[@class='css-79elbk']//button[@id='addToCartPDP']")
            driver.execute_script("arguments[0].click();", button)
            time.sleep(6)

            driver.find_element(By.XPATH, "//button[@class='sc-eCstlR ftZPut delivery-status__submit']")
            driver.execute_script("arguments[0].click();", button)
        driver.close()
    if shop == 'Метро':
        for link in links:
            driver.get(link)
            button = driver.find_element(By.XPATH, "//div[@class='css-79elbk']//button[@id='addToCartPDP']")
            driver.execute_script("arguments[0].click();", button)
            time.sleep(6)
            driver.find_element(By.XPATH, "//button[@class='sc-eCstlR ftZPut delivery-status__submit']")
            driver.execute_script("arguments[0].click();", button)
        driver.close()


# linkk(['https://www.auchan.ru/product/moloko-domik-v-derevne-ultrapasterizovannoe-3-2-950-g/', 'https://www.auchan.ru/product/pelmeni-sibirskaya-kollekciya-sibirskie-700-g/'], 'Ашан')

linkk(['https://www.perekrestok.ru/cat/417/p/pelmeni-sibirskaa-kollekcia-russkie-kategoria-v-700g-3693336', 'https://www.perekrestok.ru/cat/370/p/moloko-ultrapasterizovannoe-domik-v-derevne-3-2-925ml-2059346'], "Перекресток", "г Москва, Петровско-Разумовская аллея, д 10 к 3")