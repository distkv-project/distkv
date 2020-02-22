# HOW TO BUILD

First, enter the directory `deploy/python`.

Then execute the following command to build a wheel:
```bash
python setup.py sdist bdist_wheel
```

Last, the following command will push the wheel to pypi repository:
```bash
python -m twine upload  dist/*
```
