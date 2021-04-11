import hashlib
import urllib.request


class Downloader:

    def download(url):
        filename = url.rsplit('/', 1)[-1]
        path = '/tmp/{}'.format(filename)
        urllib.request.urlretrieve(url, '/tmp/{}'.format(filename))
        return path
