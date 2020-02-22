# coding:utf-8

import os
import subprocess
from setuptools import setup, find_packages, Distribution
import setuptools.command.build_ext as _build_ext

# A helper method to locate the current dir.
def get_current_dir():
    this_dir = os.path.dirname(os.path.realpath(__file__))
    return this_dir

class build_ext(_build_ext.build_ext):
    def run(self):
        this_dir = get_current_dir()
        build_scripts = os.path.join(this_dir, "build.sh")
        command = ["bash", build_scripts]
        subprocess.check_call(command)

class BinaryDistribution(Distribution):
    def has_ext_modules(self):
        return True

setup(
        name='dkv',
        version='0.1.3',
        description='A distributed key-value in-memory store system with table concept.',
        author='Qing Wang',
        author_email='kingchin1218@gmail.com',
        license='BSD-3-clause',
        url='https://github.com/distkv-project/distkv',
        packages=['dkv'],
        package_data={
                'dkv': ['*'],
            },
        cmdclass={"build_ext": build_ext},
        # The BinaryDistribution argument triggers build_ext.
        distclass=BinaryDistribution,
        install_requires=[],
        entry_points={
            "console_scripts": [
                "dkv-server=dkv.distkv_server:main",
                "dkv-cli=dkv.distkv_cli:main",
           ]
       },
#        include_package_data=True,
)
