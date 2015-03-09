#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os, sys, re, time, hashlib
import urllib, urllib2, json
import eyed3

# Douban API 1.0
#_doubanSearchApi    = 'http://music.douban.com/subject_search?search_text={0}&cat=1003'
#_doubanCoverPattern = '<img src="http://img(\d).douban.com/spic/s(\d+).jpg"'
#_doubanConverAddr   = 'http://img{0}.douban.com/lpic/s{1}.jpg'

# Douban API 2.0
_doubanSearchApi = r'http://api.douban.com/v2/music/search'

def image_save(img_data ,file_path):
    image_file = open(file_path, "wb")
    image_file.write(img_data)
    image_file.close()

def read_pic_from_url(url):
    retry = 5
    img = False
    while retry > 0:
        try:
            opener = urllib2.build_opener()
            opener.addheaders = [('User-agent', 'Mozilla/5.0')]
            img = opener.open(url).read()
            return img
        except Exception, e:
            print url + "is blocked"
            img_static = re.findall('img(\d)', url)[0]
            img_static_2 = (int(img_static) + 1) % retry + 1
            url = url.replace('img' + img_static, 'img' + str(img_static_2))
            retry -= 1
            time.sleep(3)
            continue

    return img

def search(id3):
    #keywords = id3.tag.artist + ' ' + (id3.tag.album or id3.tag.title)
    #print keywords
    #request = _doubanSearchApi.format(urllib2.quote(keywords))
    #print request
    if id3.tag.title:
        search_params = {u"count": 3, u"q": id3.tag.title}
        search_url = _doubanSearchApi + "?" + urllib.urlencode(search_params)
    result = urllib2.urlopen(search_url)
    if not result:
        print "url read fail"
        return False
    try:
        mp3_json = json.load(result, encoding="utf-8")
        if mp3_json['count'] > 0:
            img_url = re.sub(r'/spic/', r'/lpic/', mp3_json['musics'][0]['image'])
    except (KeyError, ValueError):
        pass
    #result_file = open("a.html", "w")
    #result_file.write(result)
    #result_file.close()

    #matcher = re.compile(_doubanCoverPattern, re.IGNORECASE).search(result)
    #if matcher:
    #    img_url = _doubanConverAddr.format(matcher.groups()[0], matcher.groups()[1])
    #else:
    #    return False

    print img_url
    if img_url:
        img_data = read_pic_from_url(img_url)
        return img_data


if __name__ == "__main__":
    audio_file = eyed3.load(os.environ.get("HOME")
            + r"/Music/test/01 - Song 6.mp3")
    img = search(audio_file)
    if img:
        image_save(img, os.environ.get("HOME")
            + r"/Music/test/a.jpg")
