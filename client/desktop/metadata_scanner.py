#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shutil
import sys
import eyed3

# TODO: eyed3 can only resolve mp3 mutagen will be used in the future
#suffix = [".mp3", ".flac", ".ape", ".wma"]
suffix = [".mp3"]

def insert_metadata(filename):
    # TODO: need file extention check
    audio_file = eyed3.load(filename)
    print "=" * 79
    print audio_file.tag.artist
    print audio_file.tag.album
    print audio_file.tag.title
    print audio_file.tag.track_num
    print "%d:%d" %(audio_file.info.time_secs / 60, audio_file.info.time_secs % 60)
    print audio_file.info.size_bytes / 1024, "KB"
    print audio_file.path
    print "=" * 79

def fetch_tag():
    pass

def cluster(filename, audio):
    artist_dir = os.path.dirname(filename) + "/" + audio.tag.artist
    album_dir = artist_dir + "/" + audio.tag.album
    print album_dir
    if not os.path.exists(album_dir):
        os.makedirs(album_dir)
    shutil.move(filename, album_dir)

def scan_dir(path):
    """This method is used to travel directories and cluster songs by tag"""
    for lists in os.listdir(path):
        tmp = os.path.join(path, lists)
        if os.path.isdir(tmp):
            #scan_dir(tmp)
            pass
        else:
            if not cmp(os.path.splitext(tmp)[1].lower(), ".mp3"):
                print tmp
                audio_file = eyed3.load(tmp)
                cluster(tmp, audio_file)

if __name__ == "__main__":
    #print os.environ.get("HOME")
    scan_dir(os.environ.get("HOME") + "/Music/test")

