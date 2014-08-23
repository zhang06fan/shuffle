#!/usr/bin/python

import os
import sys
import MySQLdb
import eyed3

def insert_metadata(filename):
    # TODO: need file extention check
    audio_file = eyed3.load(filename)
    print "========================================="
    print audio_file.tag.artist
    print audio_file.tag.album
    print audio_file.tag.title
    print audio_file.tag.track_num
    print audio_file.info.time_secs / 60, "mins", audio_file.info.time_secs % 60, "secs"
    print audio_file.info.size_bytes / 1024, "KB"
    print audio_file.path
    print "========================================="


def traverse_dir(path):
    """This method is used to travel directories and insert tags to database"""
    for lists in os.listdir(path):
        tmp = os.path.join(path, lists)
        if os.path.isdir(tmp):
            traverse_dir(tmp)
        else:
            insert_metadata(tmp)

if __name__ == "__main__":
    homedir = os.environ.get("HOME")
    traverse_dir(homedir + "/Music")
    print "done"

