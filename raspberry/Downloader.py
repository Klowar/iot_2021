import hashlib
from urllib.request import Request, urlopen



class Downloader:

    def download(url):
        filename = hashlib.md5(b'Hello World').hexdigest()
        path = '/tmp/{}'.format(filename)
        req = Request(url, headers={'User-Agent': 'Mozilla/5.0'})
        webpage = urlopen(req).read()
        with open(path, 'wb') as newfile:
            newfile.write(webpage)

        print("Downloaded {}, to {}".format(url, path))
        return path
