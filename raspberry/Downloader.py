import hashlib
import urllib.request


class Downloader:

    def download(url):
        filename = hashlib.md5(b'Hello World').hexdigest()
        path = '/tmp/{}'.format(filename)
        urllib.request.urlretrieve(url, '/tmp/{}'.format(filename))
        print("Downloaded {}, to {}".format(url, path))
        return path
