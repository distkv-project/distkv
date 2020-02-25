# coding:utf-8

import os
import shutil
import subprocess
from setuptools import setup, find_packages, Distribution
import setuptools.command.build_ext as _build_ext

# These are the files that need to be packaged to the wheel.
files_need_to_be_packaged = [
    "dkv/jars/distkv-cli.jar",
    "dkv/jars/distkv-server.jar",
]


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

        # Copy the files that need to be packaged to the build_lib,
        # so that they can be packaged to the wheel.
        for filename in files_need_to_be_packaged:
            self.move_file(filename)

    def move_file(self, filename):
        source = filename
        destination = os.path.join(self.build_lib, filename)
        # Create the target directory if it doesn't already exist.
        parent_directory = os.path.dirname(destination)
        if not os.path.exists(parent_directory):
            os.makedirs(parent_directory)
        if not os.path.exists(destination):
            print("Copying {} to {}.".format(source, destination))
            shutil.copy(source, destination, follow_symlinks=True)


class BinaryDistribution(Distribution):
    def has_ext_modules(self):
        return True


read_me_file = os.path.join(get_current_dir(), "../../README.md")
description = 'A distributed key-value in-memory store system with table concept.'
if os.path.exists(read_me_file):
    long_description = open(read_me_file).read()
else:
    long_description = description

setup(
    name='dkv',
    version='0.1.4',
    description=description,
    long_description_content_type="text/markdown",
    long_description=long_description,
    author='Qing Wang',
    author_email='kingchin1218@gmail.com',
    license='BSD-3-clause',
    url='https://github.com/distkv-project/distkv',
    packages=['dkv'],
    package_data={
        'dkv': ['*.jar'],
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
    include_package_data=True,
)
