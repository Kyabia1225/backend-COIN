# -*- coding:utf-8 -*-
import requests
from bs4 import BeautifulSoup
import json


header = {
    'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.864.41"}
cv_list=[]
director_list=[]
character_list=[]
company_list=[]

class CV:
    def __init__(self):
        self.cv_id = ""
        self.name = ""
        self.other_names = []
        self.gender = "不明"
        self.profession = ""
        self.description = ""
        self.birthday = ""


class Director:
    def __init__(self):
        self.director_id=""
        self.name=""
        self.other_names=[]
        self.gender="不明"
        self.profession=""
        self.description=""
        self.birthday=""


class Company:
    def __init__(self):
        self.company_id=""
        self.name=""
        self.other_names=[]
        self.profession=""
        self.description=""
        self.birthday=""


class Character:
    def __init__(self):
        self.character_id=""
        self.name=""
        self.gender="不明"
        self.other_names=[]
        self.description=""
        self.birthday=""


def get_cv(url):
    cv=CV()
    cv.cv_id = url.split('/')[-1]
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')
    try:
        ul = soup_html.find('ul', id='infobox').findAll('li')
        if ul:
            for li in ul:
                if li.find('span').string == "简体中文名: ":
                    cv.name = li.contents[1]
                elif li.find('span').string == "别名: ":
                    cv.other_names.append(li.contents[1])
                elif li.find('span').string == "性别: ":
                    cv.gender = li.contents[1]
                elif li.find('span').string == "生日: ":
                    cv.birthday = li.contents[1]
        detail = soup_html.find('div', class_='detail')
        if detail:
            cv.description = detail.text
        profession = soup_html.find('div', id='columnCrtB')
        if profession:
            cv.profession = profession.find('div', class_='clearit').find('h2').string[4:]
    except:
        print("解析"+url+"出错")
    return cv

def get_director(url):

    director=Director()
    director.director_id=url.split('/')[-1]
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')
    try:
        ul=soup_html.find('ul', id='infobox').findAll('li')
        if ul:
            for li in ul:
                if li.find('span').string == "简体中文名: ":
                    director.name = li.contents[1]
                elif li.find('span').string == "别名: ":
                    director.other_names.append(li.contents[1])
                elif li.find('span').string == "性别: ":
                    director.gender=li.contents[1]
                elif li.find('span').string  == "生日: ":
                    director.birthday=li.contents[1]
        detail=soup_html.find('div', class_='detail')
        if detail:
            director.description=detail.text
        profession=soup_html.find('div', id='columnCrtB')
        if profession:
            director.profession=profession.find('div', class_='clearit').find('h2').string[4:]
    except:
        print("解析"+url+"出错")
    return director


def get_company(url):
    company = Company()
    company.company_id = url.split('/')[-1]
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')
    try:
        ul = soup_html.find('ul', id='infobox').findAll('li')
        if ul:
            for li in ul:
                if li.find('span').string == "简体中文名: ":
                    company.name = li.contents[1]
                elif li.find('span').string == "别名: ":
                    company.other_names.append(li.contents[1])
                elif li.find('span').string == "生日: ":
                    company.birthday = li.contents[1]
        detail = soup_html.find('div', class_='detail')
        if detail:
            company.description = detail.text
        profession = soup_html.find('div', id='columnCrtB')
        if profession:
            company.profession = profession.find('div', class_='clearit').find('h2').string[4:]
    except:
        print("解析" + url + "出错")
    return company


def get_character(url):
    character = Character()
    character.character_id = url.split('/')[-1]
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')

    try:
        character.name=soup_html.find('h1', class_='nameSingle').find('a').string
        ul = soup_html.find('ul', id='infobox').findAll('li')
        if ul:
            for li in ul:
                if li.find('span').string == "简体中文名: ":
                    character.name = li.contents[1]
                elif li.find('span').string == "别名: ":
                    character.other_names.append(li.contents[1])
                elif li.find('span').string == "性别: ":
                    character.gender = li.contents[1]
                elif li.find('span').string == "生日: ":
                    character.birthday = li.contents[1]
        detail = soup_html.find('div', class_='detail')
        if detail:
            character.description = detail.text
    except:
        print("解析" + url + "出错")
    print('finished resolving id: '+character.character_id)
    return character


def parse_id_list(path):
    reader=open(path,'r')
    list=reader.readline()[2:-2].split('\', \'')
    if '' in list:
        list.remove('')
    reader.close()
    return list

if __name__ == '__main__':
    director_list=parse_id_list('../director_id_list')
    character_list=parse_id_list('../character_id_list')
    company_list=parse_id_list('../company_id_list')
    cv_list=parse_id_list('../cv_id_list')

    # file_director_out=open("../director_out",'a',encoding='utf-8')
    # file_cv_out=open("../cv_out",'a',encoding='utf-8')
    # file_company_out = open("../company_out", 'a', encoding='utf-8')

    #file_character_out = open("../character_out", 'a', encoding='utf-8')

    index=1
    for i in range(len(character_list)):
        if character_list[i]=='50634':
            index=i
            break

    # id lost:

    for i in range(index+1, len(character_list)):
        url="https://bgm.tv/character/"+character_list[i]
        character = get_character(url)
        with open("../character_out", 'a', encoding='utf-8') as file_character_out:
            file_character_out.write(str(character.__dict__) + ',')
    #file_character_out.close()
    # for id in character_list:
    #     url="https://bgm.tv/character/"+str(id)
    #     character=get_character(url)
    #     file_character_out.write(str(character.__dict__)+',')
    # for id in company_list:
    #     url="https://bgm.tv/person/"+str(id)
    #     company=get_company(url)
    #     file_company_out.write(str(company.__dict__)+',')
    # for id in director_list:
    #     url="https://bgm.tv/person/"+str(id)
    #     director=get_director(url)
    #     file_director_out.write(str(director.__dict__)+',')
    # for id in cv_list:
    #     url="https://bgm.tv/person/"+str(id)
    #     cv=get_cv(url)
    #     file_cv_out.write(str(cv.__dict__)+',')

    # file_cv_out.close()
    # file_director_out.close()
    # file_company_out.close()
