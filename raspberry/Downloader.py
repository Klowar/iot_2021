import hashlib
import urllib



class Downloader:

    def download(url):
        filename = hashlib.md5(url.encode('utf-8')).hexdigest()
        path = '/tmp/{}.mp3'.format(filename)
        urllib.request.urlretrieve(url, path)
        print("Downloaded {}, to {}".format(url, path))
        return path
