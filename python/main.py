# -*- coding:utf-8 -*-
import time

import requests
from bs4 import BeautifulSoup
import json


header = {
    'User-Agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.864.37"}

CV_id_list = []
director_id_list = []
character_id_list = []
anime_id_list = []
company_id_list = []


class Anime:
    def __init__(self):
        self.JapaneseName = ""
        self.title = ""
        self.length = 0
        self.startDate = ""
        self.director = ""
        self.director_id = ""
        self.company = ""
        self.company_id = ""
        self.characters = []
        self.score = 0.0
        self.ranking = ""
        self.description = ""
        self.anime_id = ""


class Character:
    def __init__(self):
        self.foreignName = ""
        self.name = ""
        self.type = ""
        self.CV = ""
        self.CV_id = ""
        self.character_id = ""


def convert2JSON(anime):
    characters = []
    for chara in anime.characters:
        # print(chara.__dict__)
        characters.append(chara.__dict__)
    return {
        'anime_id': anime.anime_id,
        'JapaneseName': anime.JapaneseName,
        'title': anime.title,
        'length': anime.length,
        'startDate': anime.startDate,
        'director': anime.director,
        'director_id': anime.director_id,
        'company':anime.company,
        'company_id':anime.company_id,
        'score': anime.score,
        'ranking': anime.ranking,
        'characters': characters,
        'description': anime.description
    }


def get_list_text(url):
    AnimeList = []
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')
    try:
        all_li = soup_html.find('ul', class_='browserFull').find_all('li')
        for div in all_li:
            url2 = 'http://bgm.tv' + div.find('div', class_='inner').find('a')['href']
            anime = getAnime(url2)
            if (anime):
                AnimeList.append(anime)
    except:
        print('解析' + url + '出错')
        file_error = open('../error_message', 'a')
        file_error.write('解析' + url + '出错\n')
        file_error.close()
    file_anime_out = open('../anime_out', 'a', encoding='utf-8')
    anime_res = ""
    for anime in AnimeList:
        anime_res += json.dumps(anime, default=convert2JSON, ensure_ascii=False) + ','
    file_anime_out.write(anime_res)
    file_anime_out.close()


def getAnime(url):
    anime = Anime()
    anime.anime_id = url.split('/')[-1]
    anime_id_list.append(anime.anime_id)
    res = requests.get(url, headers=header)
    res.encoding = 'utf-8'
    html = res.text
    soup_html = BeautifulSoup(html, 'html.parser')
    try:
        if soup_html.find('div', class_='message'):
            return
        if soup_html.find('div', id='subject_summary'):
            anime.description = soup_html.find('div', id='subject_summary').text
        name_single = soup_html.find('h1', class_='nameSingle')
        global_score = soup_html.find('div', class_='global_score')
        anime.score = global_score.find('span', class_='number').string
        anime.ranking = global_score.find('small', class_='alarm').string[1:]
        anime.JapaneseName = name_single.find('a').string
        ul = soup_html.find('ul', id='infobox').findAll('li')

        for li in ul:
            if li.find('span').string == "中文名: ":
                anime.title = li.contents[1]
            elif li.find('span').string == "话数: ":
                anime.length = li.contents[1]
            elif li.find('span').string == "放送开始: " or li.find('span').string == "上映年度: "or li.find('span').string == "开始: "or li.find('span').string == "发售日: ":
                anime.startDate = li.contents[1]
            elif li.find('span').string == "导演: " or li.find('span').string == "总导演: ":
                anime.director = li.text.split(':')[1]
                if li.find('a'):
                    anime.director_id=li.find('a')['href'].split('/')[2]
                if anime.director_id not in director_id_list:
                    director_id_list.append(anime.director_id)
            elif li.find('span').string == "动画制作: ":
                anime.company=li.text[6:]
                print(anime.company)
                if li.find('a'):
                    anime.company_id = li.find('a')['href'].split('/')[2]
                print(anime.company_id)
                if anime.company_id not in company_id_list:
                    company_id_list.append(anime.company_id)
        if soup_html.find('ul', id='browserItemList'):
            charas = soup_html.find('ul', id="browserItemList").findAll('li')
            for chara in charas:
                character = Character()
                name = ""
                CV = ""
                CV_id = ""
                character_id = chara.find('div').find('strong').find('a')['href'].split('/')[2]
                foreignName = chara.find('div').find('strong').find('a').contents[2]
                span = chara.find('div').find('div', class_='info').find('span')
                charaType = span.find('small').string
                charainfo = span.contents
                if span.find('span', class_='tip'):
                    name = span.find('span', class_='tip').string
                else:
                    name = foreignName
                if charainfo[len(charainfo) - 3] == ' CV: ':
                    CV = charainfo[len(charainfo) - 2].string
                    CV_id = charainfo[len(charainfo) - 2]['href'].split('/')[2]
                elif charainfo[len(charainfo) - 5] == ' CV: ':
                    CV = charainfo[len(charainfo) - 4].string
                    CV_id = charainfo[len(charainfo) - 4]['href'].split('/')[2]

                character.character_id = character_id
                character.CV_id = CV_id
                character.CV = CV
                character.name = name
                character.foreignName = foreignName
                character.type = charaType
                anime.characters.append(character)

                if CV_id not in CV_id_list:
                    CV_id_list.append(CV_id)
                if character_id not in character_id_list:
                    character_id_list.append(character_id)

        print("finished loading anime ranking" + str(anime.ranking))
    except:
        print('解析' + url + '出错')
        file_error = open('../error_message', 'a')
        file_error.write('解析' + url + '出错\n')
        file_error.close()
        return
    return anime

def parse_id_list(path):
    reader=open(path,'r')
    list=reader.readline()[2:-2].split('\', \'')
    reader.close()
    return list


if __name__ == '__main__':
    director_id_list=parse_id_list('../director_id_list')
    character_id_list=parse_id_list('../character_id_list')
    company_id_list=parse_id_list('../company_id_list')
    CV_id_list=parse_id_list('../cv_id_list')

    file_error = open('../error_message', 'a')
    file_error.write('-----------------------------\n')
    file_error.write('starting clawling at '+time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())+"\n")
    file_error.close()
    for i in range(1,118):
        url = 'https://bgm.tv/anime/tag/%E6%97%A5%E6%9C%AC%E5%8A%A8%E7%94%BB/?sort=rank&page=' + str(i)
        get_list_text(url)

    file_director = open('../director_id_list', 'w', encoding='utf-8')
    file_CV = open('../CV_id_list', 'w', encoding='utf-8')
    file_character = open('../character_id_list', 'w', encoding='utf-8')
    file_anime = open('../anime_id_list', 'w', encoding='utf-8')
    file_company = open('../company_id_list', 'w', encoding='utf-8')
    file_director.write(str(director_id_list))
    file_CV.write(str(CV_id_list))
    file_character.write(str(character_id_list))
    file_company.write(str(company_id_list))
    file_anime.write(str(anime_id_list))
    file_director.close()
    file_CV.close()
    file_character.close()
    file_anime.close()
    file_company.close()
    file_error = open('../error_message', 'a')
    file_error.write('finished clawling at '+time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())+"\n")
    file_error.close()