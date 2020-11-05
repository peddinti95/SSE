// Textbook RSA modified by Venkata Sandeep Peddinti
// a1758476


import time
import string

import os
import os.path
import filecmp
import string
from Crypto.PublicKey import RSA

pkfile = 'rsa_key.pub'
skfile = 'rsa_key'


def rsa_keygen():
    key = RSA.generate(4096)

    with open(skfile, 'wb') as f:
        f.write(key.exportKey('PEM'))

    pk = key.publickey()

    with open(pkfile, 'wb') as f:
        f.write(pk.exportKey('PEM'))


def rsa_encrypt(fname):
    with open(pkfile, 'rb') as f:
        sk = RSA.importKey(f.read(), 'PEM')

    with open(fname, 'r') as f:
        pt = f.read()
        if len(pt) > 128:
            pt = pt[:128]
        pt = pt.encode('ascii')

    ct = sk.encrypt(pt, 0)[0]

    with open(fname + '.enc', 'wb') as f:
        f.write(ct)


def rsa_decrypt(fname):
    with open(skfile, 'rb') as f:
        sk = RSA.importKey(f.read(), 'PEM')

    with open(fname + '.enc', 'rb') as f:
        ct = f.read()

    pt = sk.decrypt(ct).decode('ascii')
    with open(fname + '.dec', 'w') as f:
        f.write(pt)

# fname = 'ex3'
# rsa_keygen()
# rsa_encrypt(fname)
# rsa_decrypt(fname)

# rewrite encrypt to encrypt plaintext from string
def rsa_encrypt_text(plaintext, fname):
	with open(pkfile, 'rb') as f:
		sk = RSA.importKey(f.read(), 'PEM')

	pt = plaintext
	if len(pt) > 128:
		pt = pt[:128]
	pt = pt.encode('ascii')

	ct = sk.encrypt(pt, 0)[0]

	with open(fname + '.enc', 'wb') as f:
		f.write(ct)

# compare two encrypted files
def diff(f1, f2):
    with open(f1, 'rb') as f:
        s1 = f.read()
    with open(f2, 'rb') as f:
        s2 = f.read()
    if s1 == s2:
        return True
    else:
        return False

# main
print('Ex3 for Assignment 3\n')
if __name__ == '__main__':
    alphabet = string.ascii_letters[:26]
    for i in alphabet:
        for j in alphabet:
            for k in alphabet:
                rsa_encrypt_text(i+j+k, 'test')
                if diff('ex3.enc', 'test.enc'):
                    print(i+j+k)
                    break
